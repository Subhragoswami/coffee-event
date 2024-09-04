package com.coffee.event.controller;

import com.coffee.event.service.UserService;
import com.coffee.event.model.request.UserRequest;
import com.coffee.event.model.response.ResponseDto;
import com.coffee.event.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @PostMapping
    public ResponseDto<String> saveUser(@RequestBody UserRequest userRequest) {
        log.info("received request for user registration");
        return userService.saveUser(userRequest);
    }

    @GetMapping("/{userId}")
    public ResponseDto<UserResponse> getUserById(@PathVariable UUID userId){
        log.info("getting user details based on this id, {}", userId);
        return userService.getById(userId);
    }

    @GetMapping
    public ResponseDto<UserResponse> getAllUser(@PageableDefault Pageable pageable){
        log.info("getting all users");
        return userService.getALlUsers(pageable);
    }
}
