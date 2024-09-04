package com.coffee.event.service;

import com.coffee.event.dao.UserDao;
import com.coffee.event.entity.User;
import com.coffee.event.exceptions.CoffeeException;
import com.coffee.event.util.ErrorConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coffee.event.model.request.UserRequest;
import com.coffee.event.model.response.ResponseDto;
import com.coffee.event.model.response.UserResponse;
import com.coffee.event.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final ObjectMapper mapper;
    private final UserValidator userValidator;
    public ResponseDto<String> saveUser(UserRequest userRequest){
        userValidator.requestValidator(userRequest);
        User user = mapper.convertValue(userRequest, User.class);
        userDao.saveUser(user);
        return ResponseDto.<String>builder()
                .status(0)
                .data(List.of("user registered"))
                .build();
    }


    public ResponseDto<UserResponse> getById(UUID id){
        User user = userDao.getById(id)
                .orElseThrow(() -> new CoffeeException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "User")));
        UserResponse userResponse = mapper.convertValue(user, UserResponse.class);
        return ResponseDto.<UserResponse>builder()
                .status(0)
                .data(List.of(userResponse))
                .build();
    }

    public ResponseDto<UserResponse> getALlUsers(Pageable pageable){
        Page<User> users = userDao.getAll(pageable);
        List<UserResponse> userResponseList = users.stream().map((user) -> mapper.convertValue(user, UserResponse.class)).collect(Collectors.toList());
        return ResponseDto.<UserResponse>builder()
                .status(0)
                .data(userResponseList)
                .total(userResponseList.stream().count())
                .build();
    }
}
