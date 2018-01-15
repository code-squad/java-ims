package support.test;

import org.junit.Before;
import org.springframework.boot.test.web.client.TestRestTemplate;

import codesquad.domain.User;

public abstract class BasicAuthAcceptanceTest extends AcceptanceTest {
    protected TestRestTemplate basicAuthTemplate;

    protected User loginUser;

    @Before
    public void setup() {
        loginUser = findDefaultUser();
//      basicAuthTemplate 메소드는 부모인 AcceptanceTest에 있어서 바로 쓰고 있다.
        basicAuthTemplate = basicAuthTemplate(loginUser);
    }
}
