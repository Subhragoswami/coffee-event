package com.coffee.event.model.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String userEmail;
    @Builder.Default
    private boolean isActive = true;
}
