package com.github.falseGolem.LeagueManagement.controllers.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LeaguesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public @ResponseBody
    ResponseEntity<Object> handleTypeMismatchException(RuntimeException ex) {
        LeaguesError globalException = new LeaguesError();
        globalException.setCause("Looks like you're trying to send data in wrong format.");
        return handleExceptionInternal(ex, globalException, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(value = {LeaguesNpeException.class})
    public @ResponseBody
    ResponseEntity<Object> handleLeaguesNPE(RuntimeException ex) {
        LeaguesError globalException = new LeaguesError();
        globalException.setCause("Looks like you're trying to send null as param.");
        return handleExceptionInternal(ex, globalException, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        LeaguesError globalException = new LeaguesError();
        globalException.setCause("Looks like you're trying to send empty body.");
        return handleExceptionInternal(ex, globalException, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);
    }

}
