package com.coffee.event.service;

import com.coffee.event.dao.EventDao;
import com.coffee.event.entity.Event;
import com.coffee.event.model.response.EventResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coffee.event.model.request.EventFilterRequest;
import com.coffee.event.model.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventDao eventDao;
    private final ObjectMapper mapper;
    public ResponseDto<EventResponse> getAllEvents(Pageable pageable, EventFilterRequest eventFilterRequest){
        Page<Event> events;
        if(ObjectUtils.isNotEmpty(eventFilterRequest)) {
           events = eventDao.getAllEvents(pageable, eventFilterRequest.getEventName());
        }else{
           events = eventDao.getAllEvent(pageable);
        }
        List<EventResponse> eventResponseList = events.stream().map((event) -> mapper.convertValue(event, EventResponse.class)).collect(Collectors.toList());
        return ResponseDto.<EventResponse>builder()
                .status(0)
                .data(eventResponseList)
                .total(eventResponseList.stream().count())
                .build();
    }
}
