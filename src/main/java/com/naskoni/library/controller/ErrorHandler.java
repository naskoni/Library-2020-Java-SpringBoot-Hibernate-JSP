package com.naskoni.library.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception handler component that processes the exceptions thrown by all controllers
 *
 * @author Atanas Atanasov
 * @version 1.0.0
 */
@Slf4j
@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(Exception.class)
  public ModelAndView handleError(HttpServletRequest request, Exception exception) {
    log.error("Request to: {} raised: {}", request.getRequestURL(), exception, exception);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("errorMessage", "Something wrong has happened.");
    modelAndView.setViewName("error");

    return modelAndView;
  }
}
