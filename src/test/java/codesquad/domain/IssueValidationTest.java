package codesquad.domain;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;


public class IssueValidationTest {
    private static final Logger log = getLogger(IssueValidationTest.class);
    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void issueSubjectWhenIsEmpty() {
        Issue issue = new Issue("", "comment");
        Set<ConstraintViolation<Issue>> constraintViolations = validator.validate(issue);
        assertThat(constraintViolations.size(), is(1));

        for (ConstraintViolation<Issue> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }

    @Test
    public void issueCommentWhenIsEmpty() {
        Issue issue = new Issue("subject", "");
        Set<ConstraintViolation<Issue>> constraintViolations = validator.validate(issue);
        assertThat(constraintViolations.size(), is(1));

        for (ConstraintViolation<Issue> constraintViolation : constraintViolations) {
            log.debug("violation error message : {}", constraintViolation.getMessage());
        }
    }
}
