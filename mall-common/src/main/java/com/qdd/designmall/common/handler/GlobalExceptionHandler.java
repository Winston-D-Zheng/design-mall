package com.qdd.designmall.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> runtimeException(Exception ex) {
        log.error(Arrays.toString(ex.getStackTrace()));
        log.error("{}\n{}", ex.getClass().getName(), ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
