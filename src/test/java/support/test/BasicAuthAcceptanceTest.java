package support.test;

import codesquad.domain.User;
import org.junit.Before;
import org.springframework.boot.test.web.client.TestRestTemplate;

public abstract class BasicAuthAcceptanceTest extends AcceptanceTest {
    protected TestRestTemplate basicAuthTemplate;

    protected User loginUser;

    @Before
    public void setup() {
        loginUser = findDefaultUser();
        basicAuthTemplate = basicAuthTemplate(loginUser);
    }
}
