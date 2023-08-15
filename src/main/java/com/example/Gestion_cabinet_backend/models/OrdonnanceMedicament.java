package com.example.Gestion_cabinet_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medicament_ordonnance")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrdonnanceMedicament implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String posologie;
    private String quand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordonnance_id")
    @JsonIgnoreProperties("ordonnanceMedicaments")
    private OrdonnanceEntity ordonnance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicament_id")
    @JsonIgnoreProperties("ordonnanceMedicaments")
    private MedicamentEntity medicament;


}
