package com.algofusion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.algofusion.entities.Event;
import com.algofusion.requests.EventRequest;
import com.algofusion.responses.EventResponse;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    Event toEvent(EventRequest eventRequest);

    EventResponse toEventResponse(Event event);
}
