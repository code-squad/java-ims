package codesquad.web;

import codesquad.dto.MilestonesDto;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiMilestoneAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiMilestoneAcceptanceTest.class);

    @Test
    public void list() {
        makeMilestone("test subject1");
        makeMilestone("test subject2");
        makeMilestone("test subject3");

        ResponseEntity<MilestonesDto> response = template.getForEntity("/api/milestones", MilestonesDto.class);

        log.debug("body : {}", response.getBody());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void apply() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();
        ResponseEntity<String> issueResponse = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(issueResponse.getStatusCode(), Matchers.is(HttpStatus.FOUND));
        String issuePath = getResponseLocation(issueResponse);

        log.debug("path : {}", issuePath);

        ResponseEntity<String> milestoneResponse = makeMilestone("test subject1");
        String milestoneId = milestoneResponse.getHeaders().getLocation().getQuery().substring(10);

        String requestPath = issuePath + "/setMilestone/" + milestoneId;

        log.debug("path : {}", requestPath);

        ResponseEntity<String> postResponse = basicAuthTemplate().postForEntity(requestPath, null, String.class);

        assertThat(postResponse.getStatusCode(), is(HttpStatus.FOUND));
    }

    @Test
    public void apply_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();
        ResponseEntity<String> issueResponse = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(issueResponse.getStatusCode(), Matchers.is(HttpStatus.FOUND));
        String issuePath = getResponseLocation(issueResponse);

        log.debug("path : {}", issuePath);

        ResponseEntity<String> milestoneResponse = makeMilestone("test subject1");
        String milestoneId = milestoneResponse.getHeaders().getLocation().getQuery().substring(10);

        String requestPath = issuePath + "/setMilestone/" + milestoneId;

        log.debug("path : {}", requestPath);

        ResponseEntity<String> postResponse = template.postForEntity(requestPath, null, String.class);

        assertThat(postResponse.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }
}
