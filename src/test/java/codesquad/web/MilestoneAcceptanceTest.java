package codesquad.web;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneAcceptanceTest extends AcceptanceTest {

    @Test
    public void createTest() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "my subject")
                .addParameter("startDate", "2018-04-02T10:10")
                .addParameter("endDate", "2018-04-11T12:12")
                .build();

        ResponseEntity<String> response = basicAuthTemplate(findDefaultUser())
                .postForEntity("/milestones", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }
}
