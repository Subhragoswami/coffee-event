package com.coffee.event.model.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EventResponse {
    private UUID id;
    private String eventName;
    private String eventLocation;
    private Date eventStartDate;
    private Date eventEndDate;
    private Date createdAt;
    private Date updatedAt;
}
