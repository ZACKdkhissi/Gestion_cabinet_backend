package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.EventCalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCalendarRepository extends JpaRepository<EventCalendarEntity,Integer> {

}
