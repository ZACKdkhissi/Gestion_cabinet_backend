package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.models.IntervalTempsEntity;
import com.example.Gestion_cabinet_backend.models.PatientEntity;
import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import com.example.Gestion_cabinet_backend.repository.EventCalendarRepository;
import com.example.Gestion_cabinet_backend.repository.IntervalTempsRepository;
import com.example.Gestion_cabinet_backend.repository.PatientRepository;
import com.example.Gestion_cabinet_backend.repository.RendezvousRepository;
import com.example.Gestion_cabinet_backend.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class RendezvousController {
    @Autowired
    private RendezvousRepository rendezvousRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private  IntervalTempsRepository intervalTempsRepository;


    @GetMapping("/rendezvous")
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

    @PostMapping("/rendezvous")
    public ResponseEntity<RendezvousEntity> createRendezvous(@RequestBody RendezvousEntity rendezvous) {
        Optional<PatientEntity> patientOptional = patientRepository.findById(rendezvous.getPatient().getId_patient());
        if (patientOptional.isPresent()) {
            rendezvous.setPatient(patientOptional.get());
        }
        else{
            PatientEntity newPatient = new PatientEntity();
            newPatient.setId_patient(rendezvous.getPatient().getId_patient());
            newPatient.setNom(rendezvous.getPatient().getNom());
            newPatient.setPrenom(rendezvous.getPatient().getPrenom());
            newPatient.setDate_de_naissance(rendezvous.getPatient().getDate_de_naissance());
            newPatient.setSexe(rendezvous.getPatient().getSexe());
            newPatient.setCin(rendezvous.getPatient().getCin());
            newPatient.setCode_patient(newPatient.getCode_patient());
            newPatient.setPhoto_cin(rendezvous.getPatient().getPhoto_cin());
            newPatient.setTelephone(rendezvous.getPatient().getTelephone());
            newPatient.setEmail(rendezvous.getPatient().getEmail());
            newPatient.setVille(rendezvous.getPatient().getVille());
            newPatient.setMutuelle(rendezvous.getPatient().getMutuelle());
            newPatient.setCaractere(rendezvous.getPatient().getCaractere());
            newPatient.setId_parent(rendezvous.getPatient().getId_parent());
            patientService.setTypePatient(newPatient);
            newPatient.updateVerificationStatus();
            PatientEntity createdPatient = patientRepository.save(newPatient);
            rendezvous.setPatient(createdPatient);
        }

        RendezvousEntity savedRendezvous = rendezvousRepository.save(rendezvous);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRendezvous);
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
                    rendezvous.setStatut(updatedRendezvous.getStatut());
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

    @GetMapping("/rendezvous/date/{date}")
    public ResponseEntity<List<RendezvousEntity>> getRendezvousByDate(@PathVariable("date") String date) {
        List<RendezvousEntity> rendezvousList = rendezvousRepository.findByDate(date);
        return ResponseEntity.ok(rendezvousList);
    }

    @GetMapping("/rendezvous/date/count/{date}")
    public Integer getCountRendezVous(@PathVariable("date") String date){

        List<RendezvousEntity> rendezvousList = rendezvousRepository.findByDate(date);
       IntervalTempsEntity intervalExistsResponse = intervalTempsRepository.findByDate(date);


        if (intervalExistsResponse != null) {
            return ((intervalExistsResponse.getEndTime()-intervalExistsResponse.getStartTime())*2) - rendezvousList.size();
        } else {
            return 22 - rendezvousList.size();
        }

    }

    @GetMapping("/rendezvous/count/{patientId}")
    public ResponseEntity<Integer> getAppointmentCountForPatient(@PathVariable("patientId") Integer patientId) {
        Optional<PatientEntity> patientOptional = patientRepository.findById(patientId);

        if (patientOptional.isPresent()) {
            List<RendezvousEntity> rendezvousList = rendezvousRepository.findAll();
            int appointmentCount = 0;

            for (RendezvousEntity rendezvous : rendezvousList) {
                if ((rendezvous.getPatient().getId_patient() == patientId) && (rendezvous.getStatut() == 0) ) {
                    appointmentCount++;
                }
            }

            return ResponseEntity.ok(appointmentCount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }









}