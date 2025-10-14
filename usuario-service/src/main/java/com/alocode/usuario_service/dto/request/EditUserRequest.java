package com.alocode.usuario_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {
    private Boolean active;
    private Integer age;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String photo;
    private String role; // nombre del rol principal
}
