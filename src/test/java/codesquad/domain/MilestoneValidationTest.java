package codesquad.domain;

import org.junit.Before;
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
    private static final Logger log = LoggerFactory.getLogger(MilestoneValidationTest.class);
    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void invalid_subject() {
        Milestone milestone = new Milestone("", LocalDateTime.now(), LocalDateTime.now());
        Set<ConstraintViolation<Milestone>> results = validator.validate(milestone);
        assertThat(results.size(), is(1));

        for (ConstraintViolation result : results) {
            log.debug("validation error message : ", result.getMessage());
        }
    }
}
