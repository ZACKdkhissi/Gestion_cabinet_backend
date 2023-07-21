package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.models.PatientEntity;
import com.example.Gestion_cabinet_backend.repository.PatientRepository;
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
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientEntity>> getAllPatients() {
        List<PatientEntity> patients = patientRepository.findAll();
        return ResponseEntity.ok().body(patients);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientEntity> getPatientById(@PathVariable("id") Integer id) {
        Optional<PatientEntity> patientOptional = patientRepository.findById(id);
        return patientOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/patients")
    public ResponseEntity<PatientEntity> createPatient(@RequestBody PatientEntity patient) {
        patient.updateVerificationStatus();
        PatientEntity createdPatient = patientRepository.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientEntity> updatePatient(@PathVariable("id") Integer id, @RequestBody PatientEntity updatedPatient) {
        Optional<PatientEntity> patientOptional = patientRepository.findById(id);
        return patientOptional.map(patient -> {
            patient.setNom(updatedPatient.getNom());
            patient.setPrenom(updatedPatient.getPrenom());
            patient.setDate_de_naissance(updatedPatient.getDate_de_naissance());
            patient.setSexe(updatedPatient.getSexe());
            patient.setCin(updatedPatient.getCin());
            patient.setPhoto_cin(updatedPatient.getPhoto_cin());
            patient.setTelephone(updatedPatient.getTelephone());
            patient.setEmail(updatedPatient.getEmail());
            patient.setVille(updatedPatient.getVille());
            patient.setMutuelle(updatedPatient.getMutuelle());
            patient.setCaractere(updatedPatient.getCaractere());
            patient.setId_parent(updatedPatient.getId_parent());
            patient.updateVerificationStatus();
            PatientEntity updated = patientRepository.save(patient);
            return ResponseEntity.ok().body(updated);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable("id") Integer id) {
        Optional<PatientEntity> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            patientRepository.deleteById(id);
            return ResponseEntity.ok().body("Patient avec ID " + id + " est supprimé.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient non trouvé pour ID: " + id);
        }
    }
}
