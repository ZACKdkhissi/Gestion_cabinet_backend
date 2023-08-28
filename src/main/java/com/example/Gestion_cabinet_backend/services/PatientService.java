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

    public void setTypePatient(PatientEntity patientEntity) {
        if (patientEntity.getCode_patient() == null || patientEntity.getCode_patient() == 0) {
            patientEntity.setType_patient("Provisoire");
        } else {
            patientEntity.setType_patient("Officiel");
        }
    }

}
