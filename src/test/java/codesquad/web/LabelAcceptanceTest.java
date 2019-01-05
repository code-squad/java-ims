package codesquad.web;

import org.antlr.v4.runtime.misc.MultiMap;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;
import support.test.UserFixture;

public class LabelAcceptanceTest extends BasicAuthAcceptanceTest {

    @Test
    public void 라벨생성페이지이동_로그인O_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate().getForEntity("/labels/form", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 라벨생성페이지이동_로그인X_실패() {
        ResponseEntity<Void> responseEntity = template.getForEntity("/labels/form", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 라벨목록페이지이동_성공() {
        ResponseEntity<Void> responseEntity = template.getForEntity("/labels", Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 라벨생성_로그인X_실패() {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("writer", UserFixture.DOBY)
                .addParameter("subject", "Label Subject")
                .build();
        ResponseEntity<Void> responseEntity = template.postForEntity("/labels", httpEntity, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 라벨생성_로그인O_글자수_초과_실패() {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("writer", UserFixture.DOBY)
                .addParameter("subject", "Label Subject Over 20 Character")
                .build();
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY).postForEntity("/labels", httpEntity, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void 라벨생성_로그인O_글자수_정상_성공() {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("writer", UserFixture.DOBY)
                .addParameter("subject", "Label Subject")
                .build();
        ResponseEntity<Void> responseEntity = template.postForEntity("/labels", httpEntity, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void 라벨삭제_로그인X_실패() {

    }

    @Test
    public void 라벨삭제_로그인O_본인X_실패() {

    }

    @Test
    public void 라벨생성_로그인O_본인O_성공() {

    }

    @Test
    public void 라벨수정_로그인X_실패() {

    }

    @Test
    public void 라벨수정_로그인O_글자수_초과_실패() {

    }

    @Test
    public void 라벨수정_로그인O_본인X_글자수_정상_실패() {

    }

    @Test
    public void 라벨수정_로그인O_본인O_글자수_정상_성공() {

    }
}
