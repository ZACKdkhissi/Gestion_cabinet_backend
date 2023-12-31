package com.example.Gestion_cabinet_backend.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "patients")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PatientEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_patient;

    @Column(nullable = true, unique = true)
    private Integer code_patient;

    @Column(nullable = false)
    private String type_patient;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = true)
    private String date_de_naissance;

    @Column(nullable = false)
    private String sexe;

    @Column(nullable = true)
    private String cin;

    @Lob
    @Column(nullable = true ,columnDefinition = "LONGBLOB")
    private byte[] photo_cin;

    @Column(nullable = true)
    private String telephone;

    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    private int verifie;

    @Column(nullable = true)
    private String ville;

    @Column(nullable = false)
    private String mutuelle;

    @Column(nullable = true)
    private String caractere;

    @Column(nullable = true)
    private Integer id_parent;


    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("patient")
    List<SansRdvEntity> sans_rendezvous;


    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    List<RendezvousEntity> rendezvous;

    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    public void updateVerificationStatus() {
        boolean hasCinOrTelephone = (getCin() != null && !getCin().isEmpty()) || (getTelephone() != null && !getTelephone().isEmpty());

        if (hasCinOrTelephone) {
            if (getPhoto_cin() != null && getPhoto_cin().length > 0) {
                setVerifie(2);
            } else {
                setVerifie(1);
            }
        } else {
            setVerifie(0);
        }
    }
}
