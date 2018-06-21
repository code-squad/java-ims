package codesquad.security;

import codesquad.CannotDeleteException;
import codesquad.InvalidLoginInfoException;
import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class SecurityControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(SecurityControllerAdvice.class);

    public static final String INVALID_LOGIN_INFO = "로그인 정보가 올바르지 않습니다.";

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
    @ResponseStatus(value = HttpStatus.FOUND)
    public String unAuthentication() {
        log.debug("UnAuthenticationException is happened!");
        return "redirect:/users/loginForm";
    }

    @ExceptionHandler(InvalidLoginInfoException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String invalidLoginInfo(Model model) {
        log.debug("InvalidLoginInfoException is happened!");
        model.addAttribute("errorMessage", INVALID_LOGIN_INFO);
        return "/user/login";
    }

    @ExceptionHandler(CannotDeleteException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void cannotDelete() {
        log.debug("CannotDeleteException is happened!");
    }
}
