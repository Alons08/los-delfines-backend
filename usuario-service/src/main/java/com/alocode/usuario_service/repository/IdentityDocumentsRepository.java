package com.alocode.usuario_service.repository;

import com.alocode.usuario_service.entity.IdentityDocuments;
import com.alocode.usuario_service.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentityDocumentsRepository extends JpaRepository<IdentityDocuments, Integer> {
	Optional<IdentityDocuments> findByCountryAndName(Country country, String name);
}
