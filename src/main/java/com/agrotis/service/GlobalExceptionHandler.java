package com.agrotis.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Erro na aplicação: ", ex);
        
        return new ModelAndView("error");
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        logger.error("Erro de integridade de dados: ", ex);
        
        return new ModelAndView("error");
    }
}