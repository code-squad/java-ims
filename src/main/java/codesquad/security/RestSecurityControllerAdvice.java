package codesquad.security;

import codesquad.UnAuthorizedException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import support.domain.ErrorMessage;

import javax.persistence.EntityNotFoundException;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice(annotations = RestController.class)
public class RestSecurityControllerAdvice {
    private static final Logger log = getLogger(RestSecurityControllerAdvice.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> emptyResultData() {
        log.debug("EntityNotFoundException is happened!");
        return new ResponseEntity<ErrorMessage>(new ErrorMessage("EntityNotFoundException is happened!"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> unAuthorized(UnAuthorizedException e) {
        log.debug(e.getMessage());
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()), HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler(UnAuthenticationException.class)
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    public ResponseEntity<ErrorMessage> unAuthentication(UnAuthenticationException e) {
//        log.debug("!!!!!!!!!!!!!!!!!!!!! :{}", e.getMessage());
//        return new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()),HttpStatus.UNAUTHORIZED);
//    }
}
