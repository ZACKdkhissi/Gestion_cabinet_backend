package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import com.example.Gestion_cabinet_backend.repository.EventCalendarRepository;
import com.example.Gestion_cabinet_backend.repository.RendezvousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RendezvousController {
    @Autowired
    private RendezvousRepository rendezvousRepository;

    @GetMapping("/rendezvous/all")
    public ResponseEntity<List<RendezvousEntity>> getAllRendezvous() {
        List<RendezvousEntity> rendezvousList = rendezvousRepository.findAll();
        return ResponseEntity.ok(rendezvousList);
    }

    @GetMapping("/rendezvous/{id}")
    public ResponseEntity<RendezvousEntity> getRendezvousById(@PathVariable("id") Integer id) {
        Optional<RendezvousEntity> rendezvousOptional = rendezvousRepository.findById(id);
        return rendezvousOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/rendezvous/create")
    public ResponseEntity<RendezvousEntity> createRendezvous(@RequestBody RendezvousEntity rendezvous) {
        RendezvousEntity newRendezvous = rendezvousRepository.save(rendezvous);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRendezvous);
    }

    @PutMapping("/rendezvous/{id}")
    public ResponseEntity<RendezvousEntity> updateRendezvous(@PathVariable("id") Integer id, @RequestBody RendezvousEntity updatedRendezvous) {
        return rendezvousRepository.findById(id)
                .map(rendezvous -> {
                    rendezvous.setDate(updatedRendezvous.getDate());
                    rendezvous.setHeure(updatedRendezvous.getHeure());
                    rendezvous.setType(updatedRendezvous.getType());
                    rendezvous.setPatient(updatedRendezvous.getPatient());
                    rendezvous.setOrdonnance(updatedRendezvous.getOrdonnance());
                    RendezvousEntity updated = rendezvousRepository.save(rendezvous);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/rendezvous/{id}")
    public ResponseEntity<String> deleteRendezvous(@PathVariable("id") Integer id) {
        Optional<RendezvousEntity> rendezvousOptional = rendezvousRepository.findById(id);
        if (rendezvousOptional.isPresent()) {
            rendezvousRepository.deleteById(id);
            return ResponseEntity.ok("Rendezvous avec ID " + id + " est supprimé.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

