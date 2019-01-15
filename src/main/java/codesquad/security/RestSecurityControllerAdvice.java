package codesquad.security;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import support.domain.ErrorMessage;
import support.domain.Result;

import javax.persistence.EntityNotFoundException;

import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice(annotations = RestController.class)
public class RestSecurityControllerAdvice {
    private static final Logger log = getLogger(RestSecurityControllerAdvice.class);

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
    public Result unAuthentication(UnAuthenticationException e) {
        log.debug("JSON API UnAuthenticationException is happend!");
        return Result.fail(e.getMessage());
    }

//    @ExceptionHandler(UnAuthenticationException.class)
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    public ErrorMessage unAuthentication(UnAuthenticationException e) {
//        log.debug("JSON API UnAuthenticationException is happend!");
//        return new ErrorMessage(e.getMessage());
//    }
}
