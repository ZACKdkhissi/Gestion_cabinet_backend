package com.example.Gestion_cabinet_backend.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

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

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String heure;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int statut = 0;



    @ManyToOne
    @JoinColumn(name = "id_patient")
    private PatientEntity patient;

    @OneToOne(mappedBy = "rendezvous", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("ordonnance")
    private OrdonnanceEntity ordonnance;

}
