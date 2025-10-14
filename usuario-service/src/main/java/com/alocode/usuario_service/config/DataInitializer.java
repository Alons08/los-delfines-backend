package com.alocode.usuario_service.config;

import com.alocode.usuario_service.entity.Country;
import com.alocode.usuario_service.entity.IdentityDocuments;
import com.alocode.usuario_service.entity.RoleEnum;
import com.alocode.usuario_service.entity.Role;
import com.alocode.usuario_service.repository.CountryRepository;
import com.alocode.usuario_service.repository.IdentityDocumentsRepository;
import com.alocode.usuario_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


// Clase para inicializar datos en la base de datos al ejecutar la aplicación
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final IdentityDocumentsRepository identityDocumentsRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
    // Países a crear: Brasil (BRA) con CPF (11), Chile (CHL) con RUT (8), Perú (PER) con DNI (8)
    createCountryIfNotExists("BRA", "Brasil", "CPF", 11);
    createCountryIfNotExists("CHL", "Chile", "RUT", 8);
    createCountryIfNotExists("PER", "Perú", "DNI", 8);

    // Seed roles desde RoleEnum
    for (RoleEnum r : RoleEnum.values()) {
        String roleName = r.name();
        roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(Role.builder().name(roleName).description(roleName + " role").build()));
    }
    }

    private void createCountryIfNotExists(String code, String name, String idDocName, Integer digits) {
        Country country = countryRepository.findByCode(code)
                .orElseGet(() -> countryRepository.save(Country.builder().code(code).name(name).build()));

        // crear identity document para el país y tipo si no existe
        identityDocumentsRepository.findByCountryAndName(country, idDocName).orElseGet(() -> {
            IdentityDocuments identity = IdentityDocuments.builder()
                    .country(country)
                    .name(idDocName)
                    .digits(digits)
                    .build();
            return identityDocumentsRepository.save(identity);
        });
    }
}
