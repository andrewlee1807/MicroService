package com.banvien.fc.order.controller;

import com.banvien.fc.common.dto.ErrorDTO;
import com.banvien.fc.common.enums.ErrorCodeMap;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.rest.errors.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Date;

@ControllerAdvice
@EnableResourceServer
public class ExceptionHandlingController {

    @ExceptionHandler(value = { BadRequestException.class})
    public ResponseEntity<Object> handleUserNotFoundException(BadRequestException ex, WebRequest request) {
        ErrorCodeMap errorCode = ErrorCodeMap.valueOf(ex.getErrorKey());
        if (errorCode == null) {
            errorCode = ErrorCodeMap.FAILURE_EXCEPTION;
        }
        ErrorDTO error = new ErrorDTO();
        error.setErrorNo(errorCode.getValue());
        error.setErrorCode(errorCode.name());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<Object> noHandleFoundException(Exception ex, WebRequest request) {
        ErrorMessage errorObj = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        ErrorMessage errorObj = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), ex.getMessage() , request.getDescription(false));
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
