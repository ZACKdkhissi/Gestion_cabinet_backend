package com.example.Gestion_cabinet_backend.controllers;



import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;


import com.example.Gestion_cabinet_backend.config.JWTTokenHelper;
import com.example.Gestion_cabinet_backend.models.Authority;
import com.example.Gestion_cabinet_backend.models.User;
import com.example.Gestion_cabinet_backend.repository.UserDetailsRepository;
import com.example.Gestion_cabinet_backend.requests.AuthenticationRequest;
import com.example.Gestion_cabinet_backend.requests.RegistrationRequest;
import com.example.Gestion_cabinet_backend.responses.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jWTTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    private Authority createAuthority(String roleCode) {
        Authority authority=new Authority();
        authority.setRoleCode(roleCode);
        return authority;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserName(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user= (User) authentication.getPrincipal();
        if (!user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"))) {
            // L'utilisateur n'est pas un administrateur, retourner une réponse d'erreur
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String jwtToken=jWTTokenHelper.generateToken(user.getUsername());

        LoginResponse response=new LoginResponse();
        response.setToken(jwtToken);


        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(createAuthority(registrationRequest.getRole()));

        User user = new User();
        user.setUserName(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEnabled(true);
        user.setAuthorities(authorityList);

        userDetailsRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");
    }




}