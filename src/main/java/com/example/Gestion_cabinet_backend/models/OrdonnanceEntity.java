package com.example.Gestion_cabinet_backend.models;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ordonnances")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrdonnanceEntity implements Serializable {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id_ordonnance;

        @Column(nullable = false)
        private String date;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinColumn(name = "id_sansrdv")
        @JsonIgnoreProperties("ordonnance")
        private SansRdvEntity sansrdv;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinColumn(name = "id_rendezvous")
        @JsonIgnoreProperties("ordonnance")
        private RendezvousEntity rendezvous;

        @OneToMany(mappedBy = "ordonnance", cascade = CascadeType.ALL)
        private List<OrdonnanceMedicament> ordonnanceMedicaments;
}
