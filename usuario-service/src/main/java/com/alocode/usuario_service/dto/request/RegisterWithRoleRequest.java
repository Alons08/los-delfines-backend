package com.alocode.usuario_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWithRoleRequest {

    String email;
    String password;
    String firstname;
    String lastname;
    String country;
    String documentNumber;
    String phoneNumber;
    Integer age;
    Boolean userActive;
    String docType;
    String role; // role name to assign (e.g. "CUSTOMER", "ADMINISTRATOR")

}
