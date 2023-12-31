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
    private int statut = 0;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now();

    @ManyToOne
    @JsonIgnoreProperties("sans_rendezvous")
    @JoinColumn(name = "id_patient")
    private PatientEntity patient;

    @OneToOne(mappedBy = "sansrdv", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("ordonnance")
    private OrdonnanceEntity ordonnance;


}
