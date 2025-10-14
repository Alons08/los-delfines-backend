package com.alocode.usuario_service.service.impl;

import com.alocode.usuario_service.dto.request.RegisterWithRoleRequest;
import com.alocode.usuario_service.dto.response.AuthResponse;
import com.alocode.usuario_service.entity.User;
import com.alocode.usuario_service.entity.Country;
import com.alocode.usuario_service.entity.DataUser;
import com.alocode.usuario_service.entity.IdentityDocuments;
import com.alocode.usuario_service.entity.Role;
import com.alocode.usuario_service.dto.request.EditUserRequest;
import com.alocode.usuario_service.repository.UserRepository;
import com.alocode.usuario_service.security.JwtService;
import com.alocode.usuario_service.repository.CountryRepository;
import com.alocode.usuario_service.repository.DataUserRepository;
import com.alocode.usuario_service.repository.IdentityDocumentsRepository;
import com.alocode.usuario_service.repository.RoleRepository;
import com.alocode.usuario_service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.alocode.usuario_service.dto.response.UserResponse;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CountryRepository countryRepository;
    private final IdentityDocumentsRepository identityDocumentsRepository;
    private final DataUserRepository dataUserRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse registerWithRole(RegisterWithRoleRequest request) {
        // reutilizar el flujo de register pero asignando el rol pasado en request.getRole()
        var country = countryRepository.findByCode(request.getCountry())
                .orElseGet(() -> countryRepository.save(Country.builder()
                        .code(request.getCountry()).name(null).build()));

        if (request.getDocType() == null) {
            throw new IllegalArgumentException("docType is required");
        }
        IdentityDocuments identity = identityDocumentsRepository.findByCountryAndName(country, request.getDocType())
                .orElseThrow(() -> new IllegalStateException("No identity document configured for country: "
                        + country.getCode() + " and type: " + request.getDocType()));

        String dni = request.getDocumentNumber();
        String onlyDigits = dni == null ? "" : dni.replaceAll("\\D", "");
        Integer digits = onlyDigits.isEmpty() ? null : onlyDigits.length();

        if (identity.getDigits() != null && digits != null && !identity.getDigits().equals(digits)) {
            throw new IllegalArgumentException(
                    "DNI provided has " + digits + " digits but expected " + identity.getDigits());
        }

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

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(request.getUserActive() == null ? Boolean.TRUE : request.getUserActive())
                .dataUser(dataUser)
                .build();

        String roleName = request.getRole() == null ? "CUSTOMER" : request.getRole();
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository
                        .save(Role.builder().name(roleName).description("Role created by registerWithRole").build()));

        user.setRoles(new java.util.HashSet<>());
        user.getRoles().add(role);

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    @Override
    @Transactional
    public void setUserActiveStatus(Integer userId, boolean active) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setActive(active);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse editUser(Integer userId, EditUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        if (request.getActive() != null) user.setActive(request.getActive());
        DataUser dataUser = user.getDataUser();
        if (dataUser != null) {
            if (request.getAge() != null) dataUser.setAge(request.getAge());
            if (request.getFirstName() != null) dataUser.setFirstName(request.getFirstName());
            if (request.getLastName() != null) dataUser.setLastName(request.getLastName());
            if (request.getPhoneNumber() != null) dataUser.setPhoneNumber(request.getPhoneNumber());
            if (request.getPhoto() != null) dataUser.setPhoto(request.getPhoto());
            dataUserRepository.save(dataUser);
            user.setDataUser(dataUser);
        }
        if (request.getRole() != null) {
            Role role = roleRepository.findByName(request.getRole())
                .orElseGet(() -> roleRepository.save(Role.builder().name(request.getRole()).description("").build()));
            user.setRoles(new java.util.HashSet<>());
            user.getRoles().add(role);
        }
        userRepository.save(user);
        return toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse editUserByEmail(String email, EditUserRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (request.getActive() != null) user.setActive(request.getActive());
        DataUser dataUser = user.getDataUser();
        if (dataUser != null) {
            if (request.getAge() != null) dataUser.setAge(request.getAge());
            if (request.getFirstName() != null) dataUser.setFirstName(request.getFirstName());
            if (request.getLastName() != null) dataUser.setLastName(request.getLastName());
            if (request.getPhoneNumber() != null) dataUser.setPhoneNumber(request.getPhoneNumber());
            if (request.getPhoto() != null) dataUser.setPhoto(request.getPhoto());
            dataUserRepository.save(dataUser);
            user.setDataUser(dataUser);
        }
        if (request.getRole() != null) {
            Role role = roleRepository.findByName(request.getRole())
                .orElseGet(() -> roleRepository.save(Role.builder().name(request.getRole()).description("").build()));
            user.setRoles(new java.util.HashSet<>());
            user.getRoles().add(role);
        }
        userRepository.save(user);
        return toUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return toUserResponse(user);
    }

    @Override
    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream().map(this::toUserResponse).toList();
    }

    @Override
    public List<UserResponse> listCustomers() {
        return userRepository.findAll().stream()
            .filter(u -> u.getRoles() != null && u.getRoles().stream().anyMatch(r -> "CUSTOMER".equals(r.getName())))
            .map(this::toUserResponse)
            .toList();
    }

    @Override
    public UserResponse getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {
    DataUser dataUser = user.getDataUser();
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .active(user.getActive())
        .roles(user.getRoles() == null ? Set.of() : user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()))
        .dataUserId(dataUser != null ? dataUser.getId() : null)
        .documentNumber(dataUser != null ? dataUser.getDocumentNumber() : null)
        .age(dataUser != null ? dataUser.getAge() : null)
        .firstName(dataUser != null ? dataUser.getFirstName() : null)
        .lastName(dataUser != null ? dataUser.getLastName() : null)
        .phoneNumber(dataUser != null ? dataUser.getPhoneNumber() : null)
        .photo(dataUser != null ? dataUser.getPhoto() : null)
        .build();
    }
}
