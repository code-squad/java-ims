package codesquad.security;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.ErrorMessage;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice
public class SecurityRestControllerAdvice {
    private static final Logger log = getLogger(SecurityRestControllerAdvice.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage emptyResultData() {
        log.debug("EntityNotFoundException is happened!");
        return new ErrorMessage("EntityNotFoundException is happened!");
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage unAuthorized() {
        log.debug("UnAuthorizedException is happened!");
        return new ErrorMessage("UnAuthorizedException is happened!");
    }

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage unAuthentication(UnAuthenticationException e) {
        log.debug("UnAuthenticationException is happened!");
        return new ErrorMessage(e.getMessage());
    }
}
