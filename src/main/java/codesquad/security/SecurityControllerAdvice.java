package codesquad.security;

import javax.persistence.EntityNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.CannotShowException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SecurityControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(SecurityControllerAdvice.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void emptyResultData() {
        log.debug("EntityNotFoundException is happened!");
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void unAuthorized() {
        log.debug("UnAuthorizedException is happened!");
    }
    
    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public void unAuthentication() {
        log.debug("UnAuthenticationException is happened!");
    }

    @ExceptionHandler(CannotShowException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String cannotShow() {
        log.debug("CannotShowException is happened!");
        return "400.html";
    }
}
