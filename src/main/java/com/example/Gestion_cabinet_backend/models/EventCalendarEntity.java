package com.example.Gestion_cabinet_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "event_calendar")
@AllArgsConstructor
@Setter
@Getter
public class EventCalendarEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Date fromDate;
    private Date toDate;
    private String titre;
    private String description;
}
