package com.algofusion.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algofusion.common.dto.GlobalException;
import com.algofusion.entities.Event;
import com.algofusion.mapper.EventMapper;
import com.algofusion.repositories.EventRepository;
import com.algofusion.requests.EventRequest;
import com.algofusion.responses.EventResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public Event findById(Long id){
        return eventRepository.findById(id)
        .orElseThrow(()-> new GlobalException("Event not found by id: " + id.toString()));
    }

    public EventResponse createEvent(EventRequest eventRequest){
        Event event = eventMapper.toEvent(eventRequest);
        eventRepository.save(event);
        return eventMapper.toEventResponse(event);
    } 
}
