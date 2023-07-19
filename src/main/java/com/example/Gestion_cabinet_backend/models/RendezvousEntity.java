package com.example.Gestion_cabinet_backend.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "rendezvous")
@AllArgsConstructor
@Setter
@Getter
public class RendezvousEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_rdv;

    private String Date;
    private String Heure;
    private String Type;
    private int Statut;

    @ManyToOne
    @JoinColumn(name = "ID_patient")
    private PatientEntity patient;

    @OneToOne()
    @JoinColumn(name="id_ordonnance")
    private OrdonnanceEntity ordonnance;
}
