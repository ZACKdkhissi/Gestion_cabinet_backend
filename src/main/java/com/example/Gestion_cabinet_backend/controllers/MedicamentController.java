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
    public ResponseEntity<?> createMedicament(@RequestBody MedicamentEntity medicament) {
        if (medicament.getNom() == null || medicament.getNom().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("insérer le nom!!.");
        }

        boolean exists = medicamentRepository.existsByNomAndForme(medicament.getNom(), medicament.getForme());
        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deja inserer dans la base de données.");
        }

        MedicamentEntity createdMedicament = medicamentRepository.save(medicament);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedicament);
    }


    @PutMapping("/medicaments/{id}")
    public ResponseEntity<MedicamentEntity> updateMedicament(@PathVariable("id") Integer id, @RequestBody MedicamentEntity updatedMedicament) {
        Optional<MedicamentEntity> medicamentOptional = medicamentRepository.findById(id);

        if (medicamentOptional.isPresent()) {
            MedicamentEntity existingMedicament = medicamentOptional.get();

            existingMedicament.setNom(updatedMedicament.getNom());
            existingMedicament.setForme(updatedMedicament.getForme());

            MedicamentEntity updated = medicamentRepository.save(existingMedicament);
            return ResponseEntity.ok().body(updated);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicament not found with ID: " + id);
        }
    }



    @DeleteMapping("/medicaments/{id}")
    public ResponseEntity<String> deleteMedicament(@PathVariable("id") Integer id) {
        Optional<MedicamentEntity> medicamentOptional = medicamentRepository.findById(id);

        if (medicamentOptional.isPresent()) {
            medicamentRepository.deleteById(id);
            return ResponseEntity.ok().body("Medicament with ID " + id + " has been deleted.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicament not found with ID: " + id);
        }
    }


}
