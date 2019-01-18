package codesquad.web.api;

import org.junit.Test;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static codesquad.domain.UserTest.JAVAJIGI;

public class ApiIssueAcceptanceTest extends AcceptanceTest {

    @Test
    public void create_no_login() {
    }

    @Test
    public void create_login() {
        String location = createResource("/api/issues", JAVAJIGI);
        ResponseEntity<String> response = getResource(location, findDefaultUser());
//        softly.assertThat()
    }

    @Test
    public void update_no_login() {
        
    }

    @Test
    public void update_login() {
        
    }

    @Test
    public void update_other_user() {
    }

    @Test
    public void delete_no_login() {

    }
}
