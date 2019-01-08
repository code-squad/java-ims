package codesquad.security;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.UnsupportedFormatException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import support.domain.ErrorMessage;

import javax.validation.ConstraintViolationException;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice(annotations = RestController.class)
public class RestSecurityControllerAdvice {
    
    private static final Logger logger = getLogger(RestSecurityControllerAdvice.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> constraintViolation() {
        logger.debug("ConstraintViolationException is happened!");
        return new ResponseEntity(new ErrorMessage("Exception is Occurred Because Unsupported Data Format")
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessage> UnAuthentication(UnAuthenticationException e) {
        logger.debug(e.getMessage());
        return new ResponseEntity(new ErrorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> UnAuthorizedException(UnAuthorizedException e) {
        logger.debug("UnAuthorizedException is happened!");
        return new ResponseEntity(new ErrorMessage(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnsupportedFormatException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> UnsupportedLabelFormatException(UnsupportedFormatException e) {
        logger.debug(e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
