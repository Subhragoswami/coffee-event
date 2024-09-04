package com.coffee.event.model.response;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LoginResponse {
    private String token;
}
