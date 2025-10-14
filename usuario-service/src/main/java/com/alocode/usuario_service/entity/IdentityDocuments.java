package com.alocode.usuario_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "identity_documents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentityDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identity_documents_id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;

    @Column(length = 20, name = "identity_documents_name")
    String name;

    @Column(name = "identity_documents_digits")
    Integer digits;

}
