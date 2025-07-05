package com.algofusion.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algofusion.requests.EventRequest;
import com.algofusion.responses.EventResponse;
import com.algofusion.services.EventService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest eventRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(eventRequest));
    }
    
}
