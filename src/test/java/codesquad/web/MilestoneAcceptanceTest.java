package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;
import support.test.UserFixture;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest {

    @Test
    public void 마일스톤_리스트_이동() {
        ResponseEntity<Void> responseEntity = template.getForEntity("/milestone", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 마일스톤_작성_이동_로그인X_실패() {
        ResponseEntity<Void> responseEntity = template.getForEntity("/milestone/form", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 마일스톤_작성_이동_로그인O_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate().getForEntity("/milestone/form", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 마일스톤작성_로그인X_실패() {
        /* 날짜 시간 저장형식 */
        HttpEntity<MultiValueMap<String, Object>>  httpEntity = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "subject")
                .addParameter("startDate", "2019-01-26T13:01")
                .addParameter("endDate", "2019-01-26T13:02")
                .build();
        ResponseEntity<Void> responseEntity = template.postForEntity("/milestone", httpEntity, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 마일스톤작성_로그인O_SUBJECT_글자수5자미만_실패() {
        HttpEntity<MultiValueMap<String, Object>>  httpEntity = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "s")
                .addParameter("startDate", "2019-01-26T13:01")
                .addParameter("endDate", "2019-01-26T13:02")
                .build();
        ResponseEntity<Void> responseEntity = basicAuthTemplate().postForEntity("/milestone", httpEntity, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void 마일스톤작성_로그인O_SUBJECT_글자수5이상_날짜미선택_실패() {
        HttpEntity<MultiValueMap<String, Object>>  httpEntity = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "subject")
                .build();
        ResponseEntity<Void> responseEntity = basicAuthTemplate().postForEntity("/milestone", httpEntity, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void 마일스톤작성_로그인O_SUBJECT_글자수5이상_날짜선택_성공() {
        HttpEntity<MultiValueMap<String, Object>>  httpEntity = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "subject")
                .addParameter("startDate", "2019-01-26T13:01")
                .addParameter("endDate", "2019-01-26T13:02")
                .build();
        ResponseEntity<Void> responseEntity = basicAuthTemplate().postForEntity("/milestone", httpEntity, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/milestone");
    }

    @Test
    public void 마일스톤_적용_로그인X_실패() {
        ResponseEntity<Void> responseEntity = template.getForEntity("/issues/1/milestones/1", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 마일스톤_적용_로그인O_이슈작성자X_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY).getForEntity("/issues/1/milestones/1", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 마일스톤_적용_로그인O_이슈작성O_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate().getForEntity("/issues/1/milestones/1", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }
}
