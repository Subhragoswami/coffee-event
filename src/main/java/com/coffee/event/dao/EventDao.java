package com.coffee.event.dao;

import com.coffee.event.entity.Event;
import com.coffee.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class EventDao {
    private final EventRepository eventRepository;
    public Page<Event> getAllEvents(Pageable pageable, String eventName){
       return eventRepository.findAll(pageable, eventName);
    }
    public Page<Event> getAllEvent(Pageable pageable){
        return eventRepository.findAll(pageable);
    }
}
