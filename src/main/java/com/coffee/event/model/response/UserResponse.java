package com.coffee.event.model.response;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private UUID eventId;
    private String firstName;
    private String lastName;
    private String userEmail;
    private boolean isActive;
    private Date createdAt;
    private Date updatedAt;
    private Date emailRecievedAt;
}
