package codesquad.web;

import codesquad.domain.LabelRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class LabelAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(LabelAcceptanceTest.class);

    @Autowired
    private LabelRepository labelRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/labels/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response = template.getForEntity("/labels/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create() {
        ResponseEntity<String> responseEntity = makeLabel("test subject");

        String path = getResponseLocation(responseEntity);
        log.debug("path : {}", path);

        assertTrue(path.startsWith("/labels"));
    }

    @Test
    public void create_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject")
                .build();

        ResponseEntity<String> responseEntity = template.postForEntity("/labels", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void list() {
        makeLabel("test subject1");
        makeLabel("test subject2");
        makeLabel("test subject3");

        ResponseEntity<String> response = template.getForEntity("/labels", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        log.debug("body : {}", response.getBody());

        assertTrue(response.getBody().contains("test subject1"));
        assertTrue(response.getBody().contains("test subject2"));
        assertTrue(response.getBody().contains("test subject3"));
    }
}
