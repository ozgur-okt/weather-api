package com.folksdev.weather.exception;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler(RequestNotPermitted.class)
  public ResponseEntity<String> handle(RequestNotPermitted exception){
    return new ResponseEntity<>("Request limit exceeded", HttpStatus.TOO_MANY_REQUESTS);
  }
}
