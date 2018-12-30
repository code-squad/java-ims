package codesquad.security;

import codesquad.UnAuthorizedException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import support.domain.ErrorMessage;

import javax.validation.ConstraintViolationException;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice
public class RestSecurityControllerAdvice {
    
    private static final Logger logger = getLogger(RestSecurityControllerAdvice.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> unAuthorized() {
        logger.debug("ConstraintViolationException is happened!");
        return new ResponseEntity(new ErrorMessage("Exception is Occurred Because Unsupported Data Format")
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
