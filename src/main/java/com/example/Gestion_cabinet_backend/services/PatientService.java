package com.example.Gestion_cabinet_backend.services;

import com.example.Gestion_cabinet_backend.models.PatientEntity;
import com.example.Gestion_cabinet_backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void setCodePatient(PatientEntity patientEntity) {
        if ("provisoire".equalsIgnoreCase(patientEntity.getType_patient())) {
            patientEntity.setCode_patient(null);
        } else if ("officiel".equalsIgnoreCase(patientEntity.getType_patient())) {
            Integer lastCode = patientRepository.findMaxCodePatient();
            patientEntity.setCode_patient(lastCode != null ? lastCode + 1 : 1);
        }
    }
}
