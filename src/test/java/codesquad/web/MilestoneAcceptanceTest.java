package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.time.LocalDateTime;

public class MilestoneAcceptanceTest extends AcceptanceTest {

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "midlestone 제목입니다")
                .addParameter("startDate", LocalDateTime.now().toString())
                .addParameter("endDate", LocalDateTime.now().toString())
                .build();
        ResponseEntity<String> response = template.postForEntity("/milestones", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/milestones");
    }

    @Test
    public void create_invalid() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "m")
                .addParameter("startDate", LocalDateTime.now().toString())
                .addParameter("endDate", LocalDateTime.now().toString())
                .build();
        ResponseEntity<String> response = template.postForEntity("/milestones", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/milestones/form");
    }
}