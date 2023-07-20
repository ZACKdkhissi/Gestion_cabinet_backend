package com.example.Gestion_cabinet_backend.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "medicaments")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MedicamentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_medicament;

    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String forme;

    @ManyToMany(mappedBy = "medicaments")
    @JsonIgnore
    private List<OrdonnanceEntity> ordonnances;
}
