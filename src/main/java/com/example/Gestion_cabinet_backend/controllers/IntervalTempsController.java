package com.example.Gestion_cabinet_backend.controllers;


import com.example.Gestion_cabinet_backend.models.IntervalTempsEntity;
import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import com.example.Gestion_cabinet_backend.repository.IntervalTempsRepository;
import com.example.Gestion_cabinet_backend.repository.RendezvousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createIntervalTemps(@RequestBody IntervalTempsEntity intervalTemps) {
        try {
            IntervalTempsEntity existingInterval = intervalTempsRepository.findByDate(intervalTemps.getDate());

            if (existingInterval != null) {
                return new ResponseEntity<>("Ce jour déja paramétré.", HttpStatus.BAD_REQUEST);
            }

            List<RendezvousEntity> appointmentsInInterval = rendezvousRepository.findByDate(intervalTemps.getDate());

            if (!appointmentsInInterval.isEmpty()) {
                return new ResponseEntity<>("Régler les rendez-vous avant de paramétrer.", HttpStatus.BAD_REQUEST);
            }

            intervalTempsRepository.save(intervalTemps);
            return new ResponseEntity<>("Ce jour paramétrer avec succès.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Une erreur est survenue lors de la création de l'intervalle de temps.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PostMapping("/intervals")
    public ResponseEntity<String> createIntervalTemps(@RequestBody IntervalTempsEntity intervalTemps) {
        try {
            List<RendezvousEntity> appointmentsInInterval = rendezvousRepository.findByDate(intervalTemps.getDate());

            // Parse the startTime and endTime integers to LocalTime
            LocalTime startTime = LocalTime.of(intervalTemps.getStartTime(), 0); // Assuming HH format
            LocalTime endTime = LocalTime.of(intervalTemps.getEndTime(), 0);     // Assuming HH format

            // Vérifier s'il y a des rendez-vous dans l'intervalle
            boolean hasAppointmentsInInterval = appointmentsInInterval.stream()
                    .anyMatch(appointment -> isTimeWithinInterval(appointment.getHeure(), startTime, endTime));

            if (hasAppointmentsInInterval) {
                return new ResponseEntity<>("Il y a des rendez-vous dans cet intervalle. Impossible de paramétrer.", HttpStatus.BAD_REQUEST);
            }

            intervalTempsRepository.save(intervalTemps);
            return new ResponseEntity<>("Cet intervalle a été paramétré avec succès.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Une erreur est survenue lors de la création de l'intervalle de temps.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fonction pour vérifier si une heure est dans un certain intervalle
    private boolean isTimeWithinInterval(String time, LocalTime intervalStartTime, LocalTime intervalEndTime) {
        try {
            LocalTime appointmentTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")); // Assuming HH:mm format

            return !appointmentTime.isBefore(intervalStartTime) && !appointmentTime.isAfter(intervalEndTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }*/


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


}
