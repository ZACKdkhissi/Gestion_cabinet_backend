package com.example.Gestion_cabinet_backend.models;

import jakarta.persistence.*;
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
    private Date from_date;
    private Date to_date;
    private String titre;
    private String description;
}
