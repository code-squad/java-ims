package codesquad.domain;

import codesquad.dto.UserDto;
import codesquad.dto.UserDtoValidation;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import support.test.BaseTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class AttachmentValidationTest extends BaseTest {
    private static Validator validator;

    private static final Logger logger = getLogger(UserDtoValidation.class);

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void 확장자체크_성공_Test() {
        Attachment attachment = new Attachment("testFile.jpg", "", "");
        Set<ConstraintViolation<Attachment>> constraintViolcations = validator.validate(attachment);

        softly.assertThat(constraintViolcations.isEmpty()).isTrue();
        for (ConstraintViolation<Attachment> constraintViolation : constraintViolcations) {
            logger.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void 확장자체크_실패_Test() {
        Attachment attachment = new Attachment("testFile.exe", "", "");
        Set<ConstraintViolation<Attachment>> constraintViolcations = validator.validate(attachment);

        softly.assertThat(constraintViolcations.isEmpty()).isFalse();
        for (ConstraintViolation<Attachment> constraintViolation : constraintViolcations) {
            logger.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }
}
