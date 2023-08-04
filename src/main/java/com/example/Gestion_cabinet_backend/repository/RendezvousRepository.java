package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendezvousRepository extends JpaRepository<RendezvousEntity,Integer> {
    List<RendezvousEntity> findByDate(String date);

}
