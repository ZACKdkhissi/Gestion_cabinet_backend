package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.models.SansRdvEntity;
import com.example.Gestion_cabinet_backend.repository.SanRdvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SansRdvController {

    @Autowired
    private SanRdvRepository sanRdvRepository;

    @GetMapping("/sansrdv")
    public List<SansRdvEntity> getAllSansRdvs() {
        return sanRdvRepository.findAll();
    }

    @GetMapping("/sansrdv/{id}")
    public ResponseEntity<SansRdvEntity> getSansRdvById(@PathVariable("id") Integer id) {
        Optional<SansRdvEntity> sansRdvOptional = sanRdvRepository.findById(id);
        return sansRdvOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sansrdv")
    public ResponseEntity<SansRdvEntity> createSansRdv(@RequestBody SansRdvEntity sansRdv) {
        SansRdvEntity createdSansRdv = sanRdvRepository.save(sansRdv);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSansRdv);
    }

    @PutMapping("/sansrdv/{id}")
    public ResponseEntity<SansRdvEntity> updateSansRdv(@PathVariable("id") Integer id, @RequestBody SansRdvEntity updatedSansRdv) {
        return sanRdvRepository.findById(id)
                .map(sansRdv -> {
                    sansRdv.setType(updatedSansRdv.getType());
                    sansRdv.setPatient(updatedSansRdv.getPatient());
                    sansRdv.setOrdonnance(updatedSansRdv.getOrdonnance());
                    SansRdvEntity updated = sanRdvRepository.save(sansRdv);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/sansrdv/{id}")
    public ResponseEntity<Void> deleteSansRdv(@PathVariable("id") Integer id) {
        Optional<SansRdvEntity> sansRdvOptional = sanRdvRepository.findById(id);
        if (sansRdvOptional.isPresent()) {
            sanRdvRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
