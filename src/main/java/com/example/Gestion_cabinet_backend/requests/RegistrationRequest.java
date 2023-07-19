package com.example.Gestion_cabinet_backend.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequest {
    private String role;
    private String username;
    private String password;


}
