package com.alocode.usuario_service.service.impl;

import com.alocode.usuario_service.dto.request.LoginRequest;
import com.alocode.usuario_service.dto.request.RegisterRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;
import com.alocode.usuario_service.entity.User;
import com.alocode.usuario_service.entity.PasswordResetCode;
import com.alocode.usuario_service.repository.PasswordResetCodeRepository;
import com.alocode.usuario_service.service.EmailService;
import java.time.LocalDateTime;
import java.util.Random;
import com.alocode.usuario_service.repository.UserRepository;
import com.alocode.usuario_service.repository.RoleRepository;
import com.alocode.usuario_service.repository.CountryRepository;
import com.alocode.usuario_service.repository.IdentityDocumentsRepository;
import com.alocode.usuario_service.repository.DataUserRepository;
import com.alocode.usuario_service.entity.IdentityDocuments;
import com.alocode.usuario_service.entity.DataUser;
import com.alocode.usuario_service.entity.Role;
import com.alocode.usuario_service.security.JwtService;
import com.alocode.usuario_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final CountryRepository countryRepository;
        private final IdentityDocumentsRepository identityDocumentsRepository;
        private final DataUserRepository dataUserRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final PasswordResetCodeRepository passwordResetCodeRepository;
        private final EmailService emailService;
        @Override
        public void sendPasswordResetCode(String email) {
                // Generar código de 6 dígitos
                String code = String.format("%06d", new Random().nextInt(1000000));
                // Eliminar códigos previos
                passwordResetCodeRepository.deleteByEmail(email);
                // Guardar nuevo código con expiración (15 min)
                PasswordResetCode resetCode = PasswordResetCode.builder()
                        .email(email)
                        .code(code)
                        .expiresAt(LocalDateTime.now().plusMinutes(15))
                        .build();
                passwordResetCodeRepository.save(resetCode);
                // Enviar email
                emailService.sendEmail(email, "Código de recuperación", "Tu código de recuperación es: " + code);
        }

        @Override
        @Transactional
        public void resetPassword(String email, String code, String newPassword) {
                PasswordResetCode resetCode = passwordResetCodeRepository.findByEmailAndCode(email, code)
                        .orElseThrow(() -> new IllegalArgumentException("Código inválido"));
                if (resetCode.getExpiresAt().isBefore(LocalDateTime.now())) {
                        throw new IllegalArgumentException("El código ha expirado");
                }
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                passwordResetCodeRepository.deleteByEmail(email);
        }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // 1) Country: busco por code (usamos request.country como country_cod)
        var country = countryRepository.findByCode(request.getCountry())
                .orElseGet(() -> countryRepository.save(com.alocode.usuario_service.entity.Country.builder().code(request.getCountry()).name(null).build()));

        // 2) IdentityDocuments: buscar el tipo de documento para el país (ya debe existir en la BD)
        if (request.getDocType() == null) {
            throw new IllegalArgumentException("docType is required");
        }
        IdentityDocuments identity = identityDocumentsRepository.findByCountryAndName(country, request.getDocType())
                .orElseThrow(() -> new IllegalStateException("No identity document configured for country: " + country.getCode() + " and type: " + request.getDocType()));

        // validar que la longitud de dígitos del DNI coincide con lo esperado (si identity.digits está definido)
        String dni = request.getDocumentNumber();
        String onlyDigits = dni == null ? "" : dni.replaceAll("\\D", "");
        Integer digits = onlyDigits.isEmpty() ? null : onlyDigits.length();

        if (identity.getDigits() != null && digits != null && !identity.getDigits().equals(digits)) {
            throw new IllegalArgumentException("DNI provided has " + digits + " digits but expected " + identity.getDigits());
        }

        // 3) DataUser: crear con identity_documents y datos personales
        DataUser dataUser = DataUser.builder()
                .identity_documents(identity)
                .documentNumber(request.getDocumentNumber())
                .age(request.getAge())
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .phoneNumber(request.getPhoneNumber())
                .photo(null)
                .build();
        dataUser = dataUserRepository.save(dataUser);

        // 4) User: crear y ligar con dataUser
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(request.getUserActive() == null ? Boolean.TRUE : request.getUserActive())
                .dataUser(dataUser)
                .build();

        // 5) asignar rol por defecto CUSTOMER (crear si no existe)
        Role customer = roleRepository.findByName("CUSTOMER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("CUSTOMER").description("Default customer role").build()));

        user.setRoles(new java.util.HashSet<>());
        user.getRoles().add(customer);

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}