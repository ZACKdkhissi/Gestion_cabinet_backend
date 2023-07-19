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

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime timestamp;

    private String nom;
    private String prenom;
    private String type_rdv;
    private String telephone;

    @OneToOne
    @JoinColumn(name = "ID_ordonnance")
    private OrdonnanceEntity ordonnance;


}
