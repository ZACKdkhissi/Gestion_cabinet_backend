package com.example.Gestion_cabinet_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sans_rendezvous")
@AllArgsConstructor
@Setter
@Getter
public class SansRdvEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_sans_rdv;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    private String Nom;
    private String Prénom;
    private String Type_rdv;
    private String Téléphone;

    @OneToOne
    @JoinColumn(name = "ID_ordonnance")
    private OrdonnanceEntity ordonnance;


}
