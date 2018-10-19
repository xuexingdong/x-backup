package com.xuexingdong.x.wechat.exception;

import com.xuexingdong.x.common.http.XResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.Set;

@RestController
@RestControllerAdvice
public class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public XResp serverError(RuntimeException ex) {
        ex.printStackTrace();
        return XResp.internalServerError("服务器内部错误");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public XResp badRequest(ConstraintViolationException ex) {
        ex.printStackTrace();
        Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
        Optional<ConstraintViolation<?>> first = set.stream().findFirst();
        if (first.isPresent()) {
            return XResp.badRequest(first.get().getMessage());
        }
        return XResp.internalServerError("服务器内部错误");
    }
}
