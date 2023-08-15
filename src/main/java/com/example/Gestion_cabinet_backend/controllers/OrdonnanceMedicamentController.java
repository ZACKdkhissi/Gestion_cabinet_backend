package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.models.OrdonnanceMedicament;
import com.example.Gestion_cabinet_backend.repository.OrdonnanceMedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

public class OrdonnanceMedicamentController {
    @Autowired
    private OrdonnanceMedicamentRepository ordonnanceMedicamentRepository;

    @GetMapping("/ordonnance-medicaments")
    public ResponseEntity<List<OrdonnanceMedicament>> getAllOrdonnanceMedicaments() {
        List<OrdonnanceMedicament> ordonnanceMedicaments = ordonnanceMedicamentRepository.findAll();
        return ResponseEntity.ok().body(ordonnanceMedicaments);
    }

    @GetMapping("/ordonnance-medicaments/{id}")
    public ResponseEntity<OrdonnanceMedicament> getOrdonnanceMedicamentById(@PathVariable("id") Integer id) {
        Optional<OrdonnanceMedicament> ordonnanceMedicamentOptional = ordonnanceMedicamentRepository.findById(id);
        return ordonnanceMedicamentOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/ordonnance-medicaments")
    public ResponseEntity<OrdonnanceMedicament> createOrdonnanceMedicament(@RequestBody OrdonnanceMedicament ordonnanceMedicament) {
        OrdonnanceMedicament createdOrdonnanceMedicament = ordonnanceMedicamentRepository.save(ordonnanceMedicament);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrdonnanceMedicament);
    }

    @PutMapping("/ordonnance-medicaments/{id}")
    public ResponseEntity<OrdonnanceMedicament> updateOrdonnanceMedicament(@PathVariable("id") Integer id, @RequestBody OrdonnanceMedicament updatedOrdonnanceMedicament) {
        Optional<OrdonnanceMedicament> ordonnanceMedicamentOptional = ordonnanceMedicamentRepository.findById(id);

        if (ordonnanceMedicamentOptional.isPresent()) {
            OrdonnanceMedicament existingOrdonnanceMedicament = ordonnanceMedicamentOptional.get();
            // Mettez à jour les propriétés de l'ordonnance-médicament existante ici

            OrdonnanceMedicament updated = ordonnanceMedicamentRepository.save(existingOrdonnanceMedicament);
            return ResponseEntity.ok().body(updated);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordonnance-medicament not found with ID: " + id);
        }
    }

    @DeleteMapping("/ordonnance-medicaments/{id}")
    public ResponseEntity<String> deleteOrdonnanceMedicament(@PathVariable("id") Integer id) {
        Optional<OrdonnanceMedicament> ordonnanceMedicamentOptional = ordonnanceMedicamentRepository.findById(id);

        if (ordonnanceMedicamentOptional.isPresent()) {
            ordonnanceMedicamentRepository.deleteById(id);
            return ResponseEntity.ok().body("Ordonnance-medicament with ID " + id + " has been deleted.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordonnance-medicament not found with ID: " + id);
        }
    }
}
