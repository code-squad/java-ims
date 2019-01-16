package codesquad.domain;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


import static codesquad.domain.UserTest.JAVAJIGI;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LabelValidationTest {
    private static final Logger log = LoggerFactory.getLogger(UserValidationTest.class);

    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void nameWhenIsEmpty() throws Exception {
        Label label = new Label(1, "", JAVAJIGI);
        Set<ConstraintViolation<Label>> constraintViolcations = validator.validate(label);
        assertThat(constraintViolcations.size(), is(1));

        for (ConstraintViolation<Label> constraintViolation : constraintViolcations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void nameWhenIsShort() throws Exception {
        Label label = new Label(1, "a", JAVAJIGI);
        Set<ConstraintViolation<Label>> constraintViolcations = validator.validate(label);
        assertThat(constraintViolcations.size(), is(1));

        for (ConstraintViolation<Label> constraintViolation : constraintViolcations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }
}
