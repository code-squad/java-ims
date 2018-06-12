package codesquad.security;

import javax.persistence.EntityNotFoundException;

import codesquad.exception.AlreadyLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;

import static codesquad.util.Result.LOGIN_NOT_MATCH_WARNING;

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
    public String unAuthorized(Model model) {
        model.addAttribute("errorMessage", "로그인이 필요합니다.");
        log.debug("UnAuthorizedException is happened!");
        return "user/loginForm";
    }
    
    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String unAuthentication(Model model) {
        log.debug("UnAuthenticationException is happened!");
        model.addAttribute("errorMessage", LOGIN_NOT_MATCH_WARNING);
        return "user/loginForm";
    }

    @ExceptionHandler(AlreadyLoginException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String unAuthorizedAlreadyLogin() {
        log.debug("Illegal login user access is happened! ");
        return "/";
    }

//    @ExceptionHandler(LoginRequiredException.class)
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
//    public String loginRequired(Model model) {
//        log.debug("login is required");
//        model.addAttribute("errorMessage", "로그인이 필요합니다.");
//        return "user/loginForm";
//    }
}
