package net.khaibq.javabackend.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.khaibq.javabackend.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public BaseResponse<Object> handleBaseException(BaseException ex) {
        return BaseResponse.fail(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> list = new ArrayList<>();
        ex.getConstraintViolations().forEach(x -> list.add(x.getPropertyPath().toString() + " " + x.getMessage()));
        String error = String.join("\n", list);
        return BaseResponse.fail(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> list = new ArrayList<>();
        ex.getFieldErrors().forEach(x -> list.add(x.getField() + " " + x.getDefaultMessage()));
        String error = String.join(" \n", list);
        return BaseResponse.fail(error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse<Object> handleBaseException(Exception ex) {
        ex.printStackTrace();
        return BaseResponse.fail(ex.getMessage());
    }
}
