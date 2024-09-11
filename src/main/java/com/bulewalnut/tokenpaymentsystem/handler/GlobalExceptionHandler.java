package com.bulewalnut.tokenpaymentsystem.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404 오류 발생 시 처리
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(Model model) {
        model.addAttribute("errorMessage", "페이지를 찾을 수 없습니다.");
        return "error/404";
    }

    /**
     * 500 오류 발생 시 처리
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleServerError(Model model) {
        model.addAttribute("errorMessage", "서버에서 문제가 발생했습니다.");
        return "error/500";
    }
}
