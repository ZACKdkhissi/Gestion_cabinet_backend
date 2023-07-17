package com.example.Gestion_cabinet_backend.controllers;

import com.example.Gestion_cabinet_backend.repository.EventCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/EventCalendar/")
@CrossOrigin(origins = "http://localhost:3000")
public class EventCalendarController {
    @Autowired
    private EventCalendarRepository EventCalendarRepository;

}
