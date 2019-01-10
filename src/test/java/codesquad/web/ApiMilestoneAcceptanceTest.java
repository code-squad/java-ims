package codesquad.web;

import codesquad.domain.Milestone;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;
import support.test.UserFixture;

import java.util.List;

public class ApiMilestoneAcceptanceTest extends AcceptanceTest {

    @Test
    public void 마일스톤목록_셀렉트박스_성공() {
        ResponseEntity<List> milestones = template.getForEntity("/api/issues/1/milestones", List.class);
        softly.assertThat(milestones.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 마일스톤_적용_로그인X_실패() {
        ResponseEntity<Void> responseEntity = template
                .postForEntity("/api/issues/1/milestones/1", null, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 마일스톤_적용_로그인O_이슈작성자X_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/milestones/1", null, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 마일스톤_적용_로그인O_이슈작성자O_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate().postForEntity("/api/issues/1/milestones/1", null, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
