package codesquad.security;

import codesquad.UnAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import support.web.RestResponseEntityMaker;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class RestSecurityControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(RestSecurityControllerAdvice.class);

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<Void> handleRequireLogin() {
        log.debug("Rest Controller UnAuthenticationException is happened!");
        return RestResponseEntityMaker.of(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleEntityNotFound() {
        log.debug("Rest Controller EntityNotFoundException is happened!");
        return RestResponseEntityMaker.of(HttpStatus.BAD_REQUEST);
    }

}
