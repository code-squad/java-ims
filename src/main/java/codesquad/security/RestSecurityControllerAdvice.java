package codesquad.security;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.exception.AlreadyLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

import static codesquad.util.Result.LOGIN_NOT_MATCH_WARNING;

@RestControllerAdvice
public class RestSecurityControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(RestSecurityControllerAdvice.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Void> emptyResultData() {
        log.debug("EntityNotFoundException is happened!");
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<Void> unAuthorized(Model model) {
        model.addAttribute("errorMessage", "로그인이 필요합니다.");
        log.debug("UnAuthorizedException is happened!");
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> unAuthentication(Model model) {
        log.debug("UnAuthenticationException is happened!");
        model.addAttribute("errorMessage", LOGIN_NOT_MATCH_WARNING);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AlreadyLoginException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> unAuthorizedAlreadyLogin() {
        log.debug("Illegal login user access is happened! ");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
