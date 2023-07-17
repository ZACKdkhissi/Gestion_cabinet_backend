package com.example.Gestion_cabinet_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "patients")
@AllArgsConstructor
@Setter
@Getter
public class PatientEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_patient;

    private String Nom;
    private String Prénom;

    @Column(nullable = true)
    private String Date_de_naissance;

    private String Sexe;

    @Column(nullable = true)
    private String CIN;

    @Column(nullable = true)
    private String Photo_CIN;

    @Column(nullable = true)
    private String Téléphone;

    private int Vérifié;

    @Column(nullable = true)
    private String Ville;

    private String Mutuelle;

    @Column(nullable = true)
    private String caractère;

    @Column(nullable = true)
    private Double Taille;

    @Column(nullable = true)
    private Double Poids;

    @Column(nullable = true)
    private Double Glycémie;

    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<RendezvousEntity> rendezvous;
}
