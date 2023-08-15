package com.example.Gestion_cabinet_backend.controllers;
import com.example.Gestion_cabinet_backend.models.*;
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
    public ResponseEntity<OrdonnanceEntity> createOrdonnance(@RequestBody OrdonnanceEntity ordonnance) {
        if (ordonnance.getRendezvous() != null) {
            RendezvousEntity attachedRendezvous = rendezvousRepository.findById(ordonnance.getRendezvous().getId_rdv())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rendezvous not found"));
            ordonnance.setRendezvous(attachedRendezvous);
        } else if (ordonnance.getSansrdv() != null) {
            SansRdvEntity attachedSansRdv = sanRdvRepository.findById(ordonnance.getSansrdv().getId_sans_rdv())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SansRdv not found"));
            ordonnance.setSansrdv(attachedSansRdv);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ordonnance must be associated with either a rendez-vous or a sans-rendez-vous");
        }

        for (OrdonnanceMedicament medicament : ordonnance.getOrdonnanceMedicaments()) {
            MedicamentEntity attachedMedicament = medicamentRepository.findById(medicament.getMedicament().getId_medicament())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicament not found"));

            medicament.setMedicament(attachedMedicament);
            medicament.setOrdonnance(ordonnance);
        }

        OrdonnanceEntity createdOrdonnance = ordonnanceRepository.save(ordonnance);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrdonnance);
    }

    @PutMapping("/ordonnances/{id}")
    public ResponseEntity<OrdonnanceEntity> updateOrdonnance(@PathVariable("id") Integer id, @RequestBody OrdonnanceEntity updatedOrdonnance) {
        Optional<OrdonnanceEntity> ordonnanceOptional = ordonnanceRepository.findById(id);

        if (ordonnanceOptional.isPresent()) {
            OrdonnanceEntity existingOrdonnance = ordonnanceOptional.get();
            // Mettez à jour les propriétés de l'ordonnance existante ici

            OrdonnanceEntity updated = ordonnanceRepository.save(existingOrdonnance);
            return ResponseEntity.ok().body(updated);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordonnance not found with ID: " + id);
        }
    }

    @DeleteMapping("/ordonnances/{id}")
    public ResponseEntity<String> deleteOrdonnance(@PathVariable("id") Integer id) {
        Optional<OrdonnanceEntity> ordonnanceOptional = ordonnanceRepository.findById(id);

        if (ordonnanceOptional.isPresent()) {
            ordonnanceRepository.deleteById(id);
            return ResponseEntity.ok().body("Ordonnance with ID " + id + " has been deleted.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordonnance not found with ID: " + id);
        }
    }

}
