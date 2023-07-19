package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.repository.OrdonnanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Ordonnance/")
@CrossOrigin(origins = "http://localhost:3000")
public class OrdonnanceController {
    @Autowired
    private OrdonnanceRepository ordonnanceRepository;
}
