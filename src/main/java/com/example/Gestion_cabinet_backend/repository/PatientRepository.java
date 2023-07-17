package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Integer> {
}
