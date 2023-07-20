package com.example.Gestion_cabinet_backend.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event_calendar")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventCalendarEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String from_date;
    @Column(nullable = false)
    private String to_date;
    @Column(nullable = false)
    private String titre;

}
