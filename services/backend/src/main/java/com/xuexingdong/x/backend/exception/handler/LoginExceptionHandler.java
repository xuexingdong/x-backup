// package com.xxd.x.backend.exception.handler;
//
// import org.springframework.validation.BindException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
//
// @RestControllerAdvice
// public class LoginExceptionHandler {
//     @ExceptionHandler(LoginExceptionHandler.class)
//     public String badRequest(BindException ex) {
//         logger.error("{}", ex.getBindingResult().getFieldError().getDefaultMessage());
//         return ex.getBindingResult().getFieldError().getDefaultMessage();
//     }
// }
