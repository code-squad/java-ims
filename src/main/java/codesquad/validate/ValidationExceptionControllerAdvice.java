package codesquad.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class ValidationExceptionControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ValidationExceptionControllerAdvice.class);

    @Resource(name = "messageSourceAccessor")
    private MessageSourceAccessor msa;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorsResponse handleValidationException(MethodArgumentNotValidException exception) {
        /* 질문) MethodArgumentNotValidException 는 필드에 설정한 조건이 충족하지 않아 @Valid 에서 예외가 발생했을 때 생기는 예외인데
                BindingResult와 하는 역할이 동일한데 각 각 쓰이는 용도가 다른가요?!
        */
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        ValidationErrorsResponse response = new ValidationErrorsResponse();
        FieldError fieldError = (FieldError) errors.get(0);
        response.addValidationError(new ValidationError(fieldError.getField(), getErrorMessage(fieldError)));
        return response;
    }

    private String getErrorMessage(FieldError fieldError) {
        Optional<String> code = getFirstCode(fieldError);
        if (!code.isPresent()) {
            return null;
        }

        String errorMessage = msa.getMessage(code.get(), fieldError.getArguments(), fieldError.getDefaultMessage());
        log.debug("code : {}, getArguments() : {}, getDefaultMessage : {}", code, fieldError.getArguments().toString()
                , fieldError.getDefaultMessage());
        return errorMessage;
    }

    private Optional<String> getFirstCode(FieldError fieldError) {
        String[] codes = fieldError.getCodes();

        if (codes == null || codes.length == 0) {
            return Optional.empty();
        }
        return Optional.of(codes[0]);
    }
}
