package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static support.test.Fixture.LABEL_BODY;

public class LabelAcceptanceTest extends AcceptanceTest {
    HttpEntity<MultiValueMap<String, Object>> request;

    @Before
    public void setUp() throws Exception {
        request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("name", LABEL_BODY.getName())
                .addParameter("explanation", LABEL_BODY.getExplanation())
                .build();
    }

    @Test
    public void create() {
        ResponseEntity<String> response = template().postForEntity("/labels", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels");
    }

    @Test
    public void create_invalid() {
        request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("name", "a")
                .addParameter("explanation", "a")
                .build();
        ResponseEntity<String> response = template().postForEntity("/labels", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/labels/form");
    }
}