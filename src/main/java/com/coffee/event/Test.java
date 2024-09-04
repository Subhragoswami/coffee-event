package com.coffee.event;

import com.coffee.event.model.request.LoginRequest;

public class Test {
    public static void main(String[] args) throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .userName("ADMIN")
                .password("ADMIN")
                .build();


    }
}

