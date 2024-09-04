package com.coffee.event.repository;

import com.coffee.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query("FROM Event e WHERE e.eventName LIKE %:eventName%")
    Page<Event> findAll(Pageable pageable, String eventName);
}
