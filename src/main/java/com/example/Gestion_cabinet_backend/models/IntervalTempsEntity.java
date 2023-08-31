package com.example.Gestion_cabinet_backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "interval_temps")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class IntervalTempsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private Integer startTime;

    @Column(nullable = false)
    private Integer endTime;

}
