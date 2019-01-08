package codesquad.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LabelAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LogManager.getLogger(LabelAcceptanceTest.class);

    @Test
    public void listShow() {
        ResponseEntity<String> response = template.getForEntity("/label/list", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm() {
        ResponseEntity<String> response = template.getForEntity("/label/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "나는 라벨입니다.")
                .addParameter("comment","치명적인 버그")
                .build();
        ResponseEntity<String> response = template.postForEntity("/label", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/label/list");
    }
}
