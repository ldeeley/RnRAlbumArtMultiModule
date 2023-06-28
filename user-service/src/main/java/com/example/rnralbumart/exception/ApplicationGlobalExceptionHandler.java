package com.example.rnralbumart.exception;

import com.example.rnralbumart.dto.ErrorDTO;
import com.example.rnralbumart.dto.UserServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public UserServiceResponse<?> userServiceException(UserServiceException userServiceException){
        UserServiceResponse<?> userServiceResponse = new UserServiceResponse<>();
        List<ErrorDTO> errorDTOList = new ArrayList<>();
        errorDTOList.add(new ErrorDTO(userServiceException.getMessage()));
        userServiceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        userServiceResponse.setErrorDTOList(errorDTOList);
        return userServiceResponse;
    }

}
