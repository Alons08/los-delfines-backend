package com.alocode.usuario_service.repository;

import com.alocode.usuario_service.entity.DataUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataUserRepository extends JpaRepository<DataUser, Integer> {

}
