package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.PatientEntity;
import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Repository
public interface RendezvousRepository extends JpaRepository<RendezvousEntity,Integer> {
    List<RendezvousEntity> findByDate(String date);












}
