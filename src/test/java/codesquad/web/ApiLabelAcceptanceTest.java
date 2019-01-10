package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;
import support.test.UserFixture;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiLabelAcceptanceTest extends BasicAuthAcceptanceTest {

    private static final Logger logger = getLogger(ApiLabelAcceptanceTest.class);

    @Test
    public void 라벨등록_로그인X_실패() {
        ResponseEntity<Void> responseEntity = template
                .postForEntity("/api/issues/1/labels/1", new HttpEntity(new HttpHeaders()),Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    public void 라벨등록_로그인O_본인X_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/labels/1", new HttpEntity(new HttpHeaders()),Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void 라벨등록_로그인O_본인O_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate()
                .postForEntity("/api/issues/1/labels/1", new HttpEntity(new HttpHeaders()),Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 라벨_로그인O_본인O_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate()
                .postForEntity("/api/issues/1/labels/1", new HttpEntity(new HttpHeaders()),Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
