package codesquad.web.api;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static codesquad.domain.UserTest.JAVAJIGI;

public class ApiIssueAcceptanceTest extends AcceptanceTest {

    @Test
    public void create() {
        String location = createResource("/api/issues", JAVAJIGI);
        ResponseEntity<String> response = getResource(location, findDefaultUser());
//        softly.assertThat()
    }
}
