package codesquad.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ValidationControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ValidationControllerAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public String handleValidationException(HttpServletRequest request) {
        log.debug("ValidationException is happened!");
        return "redirect:" + request.getRequestURI();
    }
}
