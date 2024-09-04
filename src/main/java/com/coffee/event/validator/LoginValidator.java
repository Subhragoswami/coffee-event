package com.coffee.event.validator;

import com.coffee.event.util.ErrorConstants;
import com.coffee.event.exceptions.ValidationException;
import com.coffee.event.model.request.LoginRequest;
import com.coffee.event.model.response.ErrorDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public final class LoginValidator {

    public static void requestValidation(LoginRequest request, String appType) {
        List<ErrorDto> errorMessages = new ArrayList<>();
        if (StringUtils.isEmpty(request.getUserName())) {
            errorMessages.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "userName")));
        }
        if (StringUtils.isEmpty(request.getPassword())) {
            errorMessages.add(new ErrorDto(ErrorConstants.MANDATORY_ERROR_CODE, MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "password")));
        }

        if (CollectionUtils.isNotEmpty(errorMessages)) {
            throw new ValidationException(errorMessages);
        }
    }
}
