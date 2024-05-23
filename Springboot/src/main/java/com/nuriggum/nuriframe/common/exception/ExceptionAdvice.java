package com.nuriggum.nuriframe.common.exception;

import com.nuriggum.nuriframe.common.response.entity.CommonResult;
import com.nuriggum.nuriframe.common.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private ResponseService responseService;
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult duplicateKeyException(HttpServletRequest request, DuplicateKeyException e) {
        return responseService.getFailResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult illegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        return responseService.getFailResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult IllegalStateException(HttpServletRequest request, IllegalStateException e) {
        return responseService.getFailResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult BadCredentialsException(HttpServletRequest request, BadCredentialsException e) {
        return responseService.getFailResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
