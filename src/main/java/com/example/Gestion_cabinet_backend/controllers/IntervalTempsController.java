package com.example.Gestion_cabinet_backend.controllers;


import com.example.Gestion_cabinet_backend.models.IntervalTempsEntity;
import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import com.example.Gestion_cabinet_backend.repository.IntervalTempsRepository;
import com.example.Gestion_cabinet_backend.repository.RendezvousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class IntervalTempsController {
    private final IntervalTempsRepository intervalTempsRepository;

    @Autowired
    public IntervalTempsController(IntervalTempsRepository intervalTempsRepository) {
        this.intervalTempsRepository = intervalTempsRepository;
    }

    @Autowired
    private RendezvousRepository rendezvousRepository;

    @PostMapping("/intervals")
    public ResponseEntity<?> createIntervalTemps(@RequestBody IntervalTempsEntity intervalTemps) {
            IntervalTempsEntity savedInterval = intervalTempsRepository.save(intervalTemps);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInterval);
    }

    @GetMapping("/intervals")
    public ResponseEntity<List<IntervalTempsEntity>> getAllIntervals() {
        List<IntervalTempsEntity> intervalTempsList = intervalTempsRepository.findAll();
        return new ResponseEntity<>(intervalTempsList, HttpStatus.OK);
    }

    @GetMapping("/intervals/{date}")
    public ResponseEntity<IntervalTempsEntity> getIntervalsForDate(@PathVariable("date") String date) {
        IntervalTempsEntity interval = intervalTempsRepository.findByDate(date);

        if (interval != null) {
            return ResponseEntity.ok(interval);
        } else {
            return null;
        }
    }

    @DeleteMapping("/intervals/{id}")
    public ResponseEntity<?> deleteIntervalById(@PathVariable("id") Integer intervalId) {
        try {
            if (intervalTempsRepository.existsById(intervalId)) {
                intervalTempsRepository.deleteById(intervalId);
                return ResponseEntity.ok("Interval deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting interval: " + e.getMessage());
        }
    }


}
