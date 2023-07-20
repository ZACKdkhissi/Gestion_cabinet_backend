package com.example.Gestion_cabinet_backend.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sans_rendezvous")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SansRdvEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sans_rdv;


    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int statut;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private PatientEntity patient;

    @OneToOne
    @JoinColumn(name = "id_ordonnance")
    private OrdonnanceEntity ordonnance;


}
