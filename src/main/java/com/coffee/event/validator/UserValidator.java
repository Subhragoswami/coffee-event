package com.coffee.event.validator;

import com.coffee.event.dao.UserDao;
import com.coffee.event.model.request.UserRequest;
import com.coffee.event.util.ErrorConstants;
import com.coffee.event.exceptions.ValidationException;
import com.coffee.event.model.response.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserDao userDao;
    public void requestValidator(UserRequest userRequest){
        List<ErrorDto> errorDtoList =new ArrayList<>();
        if(ObjectUtils.isEmpty(userRequest)){
            errorDtoList.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "request body can't be empty")));
        }
        if(userDao.existsByUserEmail(userRequest.getUserEmail())){
            errorDtoList.add(new ErrorDto(ErrorConstants.ALREADY_PRESENT_ERROR_CODE, MessageFormat.format(ErrorConstants.ALREADY_PRESENT_ERROR_MESSAGE, "duplicate email ", "email")));
        }
        if (CollectionUtils.isNotEmpty(errorDtoList)) {
            throw new ValidationException(errorDtoList);
        }
    }
}
