package codesquad.dto;

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

public class UserDtoValidation extends BaseTest {

    private static Validator validator;

    private static final Logger logger = getLogger(UserDtoValidation.class);

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void userId_공백5글자_Test() {
        UserDto userDto = new UserDto("     ", "password", "DOBY");
        Set<ConstraintViolation<UserDto>> constraintViolcations = validator.validate(userDto);
        assertThat(constraintViolcations.size(), is(1));

        for (ConstraintViolation<UserDto> constraintViolation : constraintViolcations) {
            logger.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void passwrod_3글자_Test() {
        UserDto userDto = new UserDto("testUser", "pwd", "DOBY");
        Set<ConstraintViolation<UserDto>> constraintViolcations = validator.validate(userDto);
        assertThat(constraintViolcations.size(), is(1));

        for (ConstraintViolation<UserDto> constraintViolation : constraintViolcations) {
            logger.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }
}
