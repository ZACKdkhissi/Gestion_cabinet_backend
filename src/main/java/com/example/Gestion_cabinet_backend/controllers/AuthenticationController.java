package com.example.Gestion_cabinet_backend.controllers;



import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.*;


import com.example.Gestion_cabinet_backend.config.JWTTokenHelper;
import com.example.Gestion_cabinet_backend.models.Authority;
import com.example.Gestion_cabinet_backend.models.User;
import com.example.Gestion_cabinet_backend.repository.UserDetailsRepository;
import com.example.Gestion_cabinet_backend.requests.AuthenticationRequest;
import com.example.Gestion_cabinet_backend.requests.RegistrationRequest;
import com.example.Gestion_cabinet_backend.responses.LoginResponse;
import com.example.Gestion_cabinet_backend.responses.UserInfo;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
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
        String jwtToken=jWTTokenHelper.generateToken(user.getUsername());

        LoginResponse response=new LoginResponse();
        response.setToken(jwtToken);


        return ResponseEntity.ok(response);
    }

        @GetMapping("/users")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
            List<User> userList = userDetailsRepository.findAll();


            List<Map<String, Object>> usersData = new ArrayList<>();

            for (User user : userList) {
                Map<String, Object> userData = new HashMap<>();
                userData.put("id", user.getId());
                userData.put("username", user.getUsername());
                userData.put("email", user.getEmail());
                usersData.add(userData);
            }

            return ResponseEntity.ok(usersData);
        }





    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        List<Authority> authorityList = new ArrayList<>();
        for (String role : registrationRequest.getRoles()) {
            authorityList.add(createAuthority(role));
        }

        User user = new User();
        user.setUserName(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEnabled(true);
        user.setAuthorities(authorityList);
        user.setEmail(registrationRequest.getEmail());


        userDetailsRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        Optional<User> optionalUser = userDetailsRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userDetailsRepository.delete(user);
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user){
        User userObj=(User) userDetailsService.loadUserByUsername(user.getName());

        UserInfo userInfo=new UserInfo();
        userInfo.setUserName(userObj.getUsername());
        userInfo.setRoles(userObj.getAuthorities().toArray());

        return ResponseEntity.ok(userInfo);

    }





}
