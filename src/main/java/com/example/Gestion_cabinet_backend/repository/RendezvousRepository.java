package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.RendezvousEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RendezvousRepository extends JpaRepository<RendezvousEntity,Integer> {
}
