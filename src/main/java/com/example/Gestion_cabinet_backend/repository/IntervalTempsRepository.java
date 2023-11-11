package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.IntervalTempsEntity;
import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IntervalTempsRepository extends JpaRepository<IntervalTempsEntity,Integer> {
    IntervalTempsEntity findByDate(String date);


}
