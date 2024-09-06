package com.coffee.event.service;

import com.coffee.event.dao.UserDao;
import com.coffee.event.entity.User;
import com.coffee.event.event.SendEmailEvent;
import com.coffee.event.exceptions.CoffeeException;
import com.coffee.event.util.CsvUtil;
import com.coffee.event.util.ErrorConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coffee.event.model.request.UserRequest;
import com.coffee.event.model.response.ResponseDto;
import com.coffee.event.model.response.UserResponse;
import com.coffee.event.validator.UserValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    private final CsvUtil csvUtil;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final ApplicationEventPublisher eventPublisher; // Add ApplicationEventPublisher

    public ResponseDto<String> saveUser(UserRequest userRequest) {
        userValidator.requestValidator(userRequest);
        User user = mapper.convertValue(userRequest, User.class);
        userDao.saveUser(user);
        eventPublisher.publishEvent(new SendEmailEvent(this, user));

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

    public ResponseDto<String> downloadCsv(HttpServletResponse response){
        String headers = "User Id, User Email, User first name, user last name, event Id, email received at, created at, updated at";
        List<String> csvContent = new ArrayList<>();
        List<User> users = userDao.getAllUsers();
        csvUtil.organizeCsv(csvContent, users);
        csvUtil.downloadCSVFile(headers, csvContent, response, "User", "csv");
        return ResponseDto.<String>builder()
                .status(0)
                .data(List.of("downloaded successfullly"))
                .build();
    }
    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
