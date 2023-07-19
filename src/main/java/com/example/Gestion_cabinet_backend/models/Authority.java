package com.example.Gestion_cabinet_backend.models;



import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Table(name = "AUTH_AUTHORITY")
@Entity
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return roleCode;
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getRoleCode() {
        return roleCode;
    }



    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }




}
