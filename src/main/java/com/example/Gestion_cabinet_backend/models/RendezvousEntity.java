package com.example.Gestion_cabinet_backend.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "rendezvous")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RendezvousEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rdv;

    private String date;
    private String heure;
    private String type;
    private int statut;

    @ManyToOne
    @JoinColumn(name = "ID_patient")
    private PatientEntity patient;

    @OneToOne()
    @JoinColumn(name="id_ordonnance")
    private OrdonnanceEntity ordonnance;
}
