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

    @Column(nullable = true)
    private String forme;

    @Column(nullable = true)
    private String substance ;

    @Column(nullable = true)
    private String prix ;

    @Column(nullable = true)
    private String laboratoire ;

    @Column(nullable = true)
    private String type;




    @OneToMany(mappedBy = "medicament", cascade = CascadeType.ALL)
    private List<OrdonnanceMedicament> ordonnanceMedicaments;
}
