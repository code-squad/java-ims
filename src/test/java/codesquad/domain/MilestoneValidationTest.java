package codesquad.domain;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneValidationTest {
    private static final Logger log = LoggerFactory.getLogger(UserValidationTest.class);

    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void subjectWhenIsEmpty() throws Exception {
        Milestone milestone = new Milestone(1, "", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<Milestone>> constraintViolcations = validator.validate(milestone);
        assertThat(constraintViolcations.size(), is(1));

        for (ConstraintViolation<Milestone> constraintViolation : constraintViolcations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void subjectWhenIsShort() throws Exception {
        Milestone milestone = new Milestone(1, "m", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<Milestone>> constraintViolcations = validator.validate(milestone);
        assertThat(constraintViolcations.size(), is(1));

        for (ConstraintViolation<Milestone> constraintViolation : constraintViolcations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

}
