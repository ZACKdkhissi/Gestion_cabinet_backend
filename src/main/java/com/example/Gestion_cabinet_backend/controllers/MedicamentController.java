package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.models.MedicamentEntity;
import com.example.Gestion_cabinet_backend.models.OrdonnanceEntity;
import com.example.Gestion_cabinet_backend.repository.EventCalendarRepository;
import com.example.Gestion_cabinet_backend.repository.MedicamentRepository;
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
public class MedicamentController {
    @Autowired
    private MedicamentRepository medicamentRepository;


    @GetMapping("/medicaments")
    public ResponseEntity<List<MedicamentEntity>> getAllMedicaments() {
        List<MedicamentEntity> medicaments = medicamentRepository.findAll();
        return ResponseEntity.ok().body(medicaments);
    }

    @GetMapping("/medicaments/{id}")
    public ResponseEntity<MedicamentEntity> getMedicamentById(@PathVariable("id") Integer id) {
        Optional<MedicamentEntity> medicamentOptional = medicamentRepository.findById(id);
        return medicamentOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/medicaments")
    public ResponseEntity<MedicamentEntity> createMedicament(@RequestBody MedicamentEntity medicament) {
        MedicamentEntity createdMedicament = medicamentRepository.save(medicament);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedicament);
    }

    @PutMapping("/medicaments/{id}")
    public ResponseEntity<MedicamentEntity> updateMedicament(@PathVariable("id") Integer id, @RequestBody MedicamentEntity updatedMedicament) {
        Optional<MedicamentEntity> medicamentOptional = medicamentRepository.findById(id);
        return medicamentOptional.map(medicament -> {
            medicament.setNom(updatedMedicament.getNom());
            medicament.setForme(updatedMedicament.getForme());
            MedicamentEntity updated = medicamentRepository.save(medicament);
            return ResponseEntity.ok().body(updated);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/medicaments/{id}")
    public ResponseEntity<String> deleteMedicament(@PathVariable("id") Integer id) {
        Optional<MedicamentEntity> medicamentOptional = medicamentRepository.findById(id);
        if (medicamentOptional.isPresent()) {
            MedicamentEntity existingMedicament = medicamentOptional.get();
            List<OrdonnanceEntity> ordonnances = existingMedicament.getOrdonnances();
            if (ordonnances.isEmpty()) {
                medicamentRepository.deleteById(id);
                return ResponseEntity.ok().body("Medicament with ID " + id + " has been deleted.");
            } else {
                return ResponseEntity.badRequest().body("Cannot delete the medicament with ID " + id + " as it is associated with ordonnances.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicament not found with ID: " + id);
        }
    }
}
