package com.example.Gestion_cabinet_backend.controllers;
import com.example.Gestion_cabinet_backend.models.MedicamentEntity;
import com.example.Gestion_cabinet_backend.models.OrdonnanceEntity;
import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import com.example.Gestion_cabinet_backend.models.SansRdvEntity;
import com.example.Gestion_cabinet_backend.repository.MedicamentRepository;
import com.example.Gestion_cabinet_backend.repository.OrdonnanceRepository;
import com.example.Gestion_cabinet_backend.repository.RendezvousRepository;
import com.example.Gestion_cabinet_backend.repository.SanRdvRepository;
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
public class OrdonnanceController {
    @Autowired
    private OrdonnanceRepository ordonnanceRepository;
    @Autowired
    private MedicamentRepository medicamentRepository;
    @Autowired
    private RendezvousRepository rendezvousRepository;
    @Autowired
    private SanRdvRepository sanRdvRepository;

    @GetMapping("/ordonnances")
    public ResponseEntity<List<OrdonnanceEntity>> getAllOrdonnances() {
        List<OrdonnanceEntity> ordonnances = ordonnanceRepository.findAll();
        return ResponseEntity.ok().body(ordonnances);
    }

    @GetMapping("/ordonnances/{id}")
    public ResponseEntity<OrdonnanceEntity> getOrdonnanceById(@PathVariable("id") Integer id) {
        Optional<OrdonnanceEntity> ordonnanceOptional = ordonnanceRepository.findById(id);
        return ordonnanceOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/ordonnances")
    public ResponseEntity<String> createOrdonnance(@RequestBody OrdonnanceEntity ordonnanceEntity) {
        RendezvousEntity rendezvous = ordonnanceEntity.getRendezvous();
        SansRdvEntity sansrdv = ordonnanceEntity.getSansrdv();

        if (rendezvous == null && sansrdv == null) {
            return ResponseEntity.badRequest().body("Either rendezvous or sansrdv field must be filled");
        }

        List<MedicamentEntity> medicaments = ordonnanceEntity.getMedicaments();
        for (MedicamentEntity medicament : medicaments) {
            Optional<MedicamentEntity> medicamentOptional = medicamentRepository.findById(medicament.getId_medicament());

            if (medicamentOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("One or more medicaments not found");
            }
        }

        if (rendezvous != null) {
            RendezvousEntity existingRendezvous = rendezvousRepository.findById(rendezvous.getId_rdv())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rendezvous not found"));
            ordonnanceEntity.setRendezvous(existingRendezvous);
        }

        if (sansrdv != null) {
            SansRdvEntity existingSansRdv = sanRdvRepository.findById(sansrdv.getId_sans_rdv())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SansRdv not found"));
            ordonnanceEntity.setSansrdv(existingSansRdv);
        }
        ordonnanceRepository.save(ordonnanceEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ordonnance created successfully");
    }

    @PutMapping("/ordonnances/{id}")
    public ResponseEntity<OrdonnanceEntity> updateOrdonnance(@PathVariable("id") Integer id, @RequestBody OrdonnanceEntity updatedOrdonnance) {
        OrdonnanceEntity ordonnance = ordonnanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ordonnance.setDate(updatedOrdonnance.getDate());
        ordonnance.setMedicaments(updatedOrdonnance.getMedicaments());
        ordonnanceRepository.save(ordonnance);

        return ResponseEntity.ok(ordonnance);
    }


    @DeleteMapping("/ordonnances/{id}")
    public ResponseEntity<String> deleteOrdonnance(@PathVariable("id") Integer id) {
        Optional<OrdonnanceEntity> ordonnanceOptional = ordonnanceRepository.findById(id);
        if (ordonnanceOptional.isPresent()) {
            OrdonnanceEntity existingOrdonnance = ordonnanceOptional.get();
            List<MedicamentEntity> medicaments = existingOrdonnance.getMedicaments();
            if (medicaments.isEmpty()) {
                ordonnanceRepository.deleteById(id);
                return ResponseEntity.ok("Ordonnance with ID " + id + " has been deleted.");
            } else {
                return ResponseEntity.badRequest().body("Cannot delete the ordonnance with ID " + id + " as it is associated with medicaments.");
            }
        } else {
            return ResponseEntity.badRequest().body("Ordonnance not found with ID: " + id);
        }

    }
}
