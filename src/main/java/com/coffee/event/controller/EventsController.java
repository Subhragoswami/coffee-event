package com.coffee.event.controller;

import com.coffee.event.model.request.EventFilterRequest;
import com.coffee.event.model.response.EventResponse;
import com.coffee.event.model.response.ResponseDto;
import com.coffee.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventsController {
    private final EventService eventService;
    @PostMapping
    public ResponseDto<EventResponse> getEvents(@PageableDefault Pageable pageable,
                                                @RequestBody(required = false) EventFilterRequest eventFilterRequest){
        log.info("received request for getting all event");
        return eventService.getAllEvents(pageable, eventFilterRequest);
    }
}
