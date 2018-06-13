package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueValidationTest {
    private static final Logger log = LoggerFactory.getLogger(IssueValidationTest.class);

    private static Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void invalid_title() {
        Issue issue = new Issue("t", "contents");
        Set<ConstraintViolation<Issue>> results = validator.validate(issue);
        assertThat(results.size(), is(1));

        for (ConstraintViolation violation : results) {
            log.debug("validation err result : {}", violation.getMessage());
        }
    }

    @Test
    public void invalid_Issue_params() {
        Issue issue = new Issue("t", "c");
        Set<ConstraintViolation<Issue>> results = validator.validate(issue);
        assertThat(results.size(), is(2));
    }
}