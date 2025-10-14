package com.alocode.usuario_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "data_users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_user_id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "identity_documents_id", nullable = false)
    IdentityDocuments identity_documents;

    @Column(name = "data_user_document_number", unique = true, nullable = false)
    String documentNumber;

    Integer age;
    @Column(length = 30, name = "data_user_first_name")
    String firstName;

    @Column(length = 50, name = "data_user_last_name")
    String lastName;

    @Column(length = 50, name = "data_user_phone_number", unique = true)
    String phoneNumber;

    @Column(length = 255, name = "data_user_photo")
    String photo;

}
