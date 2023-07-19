package com.example.Gestion_cabinet_backend.repository;

import com.example.Gestion_cabinet_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
}
