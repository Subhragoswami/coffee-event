package com.coffee.event.service;

import com.coffee.event.model.request.LoginRequest;
import com.coffee.event.model.response.LoginResponse;
import com.coffee.event.model.response.ResponseDto;
import com.coffee.event.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final AuthenticationService authenticationService;

    public ResponseDto<LoginResponse> login(LoginRequest requestBody) {
        LoginResponse loginResponse = new LoginResponse(authenticationService.signIn(requestBody));
        return ResponseDto.<LoginResponse>builder().status(0).data(List.of(loginResponse)).build();
    }
}


