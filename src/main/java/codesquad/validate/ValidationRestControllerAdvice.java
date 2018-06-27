package codesquad.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationRestControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ValidationRestControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleInvalidMethodArgument() {
        log.debug("RestController MethodArgumentNotValidException is happened!!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
