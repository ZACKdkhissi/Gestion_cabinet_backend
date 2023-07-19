package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.models.EventCalendarEntity;
import com.example.Gestion_cabinet_backend.repository.EventCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class EventCalendarController {
    @Autowired
    private EventCalendarRepository eventCalendarRepository;

    @GetMapping("/events")
    public ResponseEntity<List<EventCalendarEntity>> getAllEvents() {
        List<EventCalendarEntity> events = eventCalendarRepository.findAll();
        return ResponseEntity.ok().body(events);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventCalendarEntity> getEventById(@PathVariable("id") Integer id) {
        Optional<EventCalendarEntity> eventOptional = eventCalendarRepository.findById(id);
        return eventOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/events")
    public ResponseEntity<EventCalendarEntity> createEvent(@RequestBody EventCalendarEntity event) {
        EventCalendarEntity createdEvent = eventCalendarRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventCalendarEntity> updateEvent(@PathVariable("id") Integer id, @RequestBody EventCalendarEntity updatedEvent) {
        Optional<EventCalendarEntity> eventOptional = eventCalendarRepository.findById(id);
        return eventOptional.map(event -> {
            event.setFrom_date(updatedEvent.getFrom_date());
            event.setTo_date(updatedEvent.getTo_date());
            event.setTitre(updatedEvent.getTitre());
            event.setDescription(updatedEvent.getDescription());
            EventCalendarEntity updated = eventCalendarRepository.save(event);
            return ResponseEntity.ok().body(updated);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/events/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Integer id) {
        Optional<EventCalendarEntity> eventOptional = eventCalendarRepository.findById(id);
        if (eventOptional.isPresent()) {
            eventCalendarRepository.deleteById(id);
            return ResponseEntity.ok().body("Event with ID " + id + " has been deleted.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with ID: " + id);
        }
    }
}
