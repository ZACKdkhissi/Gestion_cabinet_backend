package com.example.Gestion_cabinet_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ordonnances")
@AllArgsConstructor
@Setter
@Getter
public class OrdonnanceEntity implements Serializable {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer ID_ordonnance;

        private String Date;

        @OneToOne(mappedBy = "ordonnance",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        private RendezvousEntity rendezvous;

        @ManyToMany
        @JoinTable(
                name = "ordonnance_medicament",
                joinColumns = @JoinColumn(name = "ID_ordonnance"),
                inverseJoinColumns = @JoinColumn(name = "ID_medicament")
        )
        private List<MedicamentEntity> medicaments;


        // Getters and setters

}
