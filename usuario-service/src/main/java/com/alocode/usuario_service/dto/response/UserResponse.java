package com.alocode.usuario_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String email;
    private Boolean active;
    private Set<String> roles;
    // Datos de DataUser
    private Integer dataUserId;
    private String documentNumber;
    private Integer age;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String photo;
}
