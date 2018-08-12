package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LabelAcceptanceTest extends AcceptanceTest {
    @Test
    public void list() {
        ResponseEntity<String> response = template.getForEntity("/labels", String.class);
        assertThat(response.getStatusCode() ,is(HttpStatus.OK));
    }

    @Test
    public void createForm() {
        ResponseEntity<String> response = template.getForEntity("/labels/form", String.class);
        assertThat(response.getStatusCode() ,is(HttpStatus.OK));
    }
}
