package codesquad.web;

import codesquad.domain.IssueRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LogManager.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;


    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issue/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다.")
                .addParameter("comment", "내용입니다.").build();

        ResponseEntity<String> response = template.postForEntity("/issue", request, String.class);
        log.debug(response.getStatusCode());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(issueRepository.findById(1L)).isNotEmpty();
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }
}

