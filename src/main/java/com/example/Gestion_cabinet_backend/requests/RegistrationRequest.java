package com.example.Gestion_cabinet_backend.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequest {
    private List<String> roles;
    private String username;
    private String password;
    private String email;


}
