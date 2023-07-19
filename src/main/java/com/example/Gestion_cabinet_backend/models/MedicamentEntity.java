package com.example.Gestion_cabinet_backend.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "medicaments")
@AllArgsConstructor
@Setter
@Getter
public class MedicamentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_medicament;

    private String Nom;
    private String Forme;

    @ManyToMany(mappedBy = "medicaments")
    private List<OrdonnanceEntity> ordonnances;
}
