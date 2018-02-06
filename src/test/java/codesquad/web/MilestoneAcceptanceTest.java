package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Autowired
    private MilestoneRepository milestoneRepository;


    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "마일스톤입니다")
                .addParameter("startDate", "2017-06-01T08:30")
                .addParameter("endDate", "2017-06-02T08:30")
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestones", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        log.debug("it's saved milestone: {}", milestoneRepository.findAll());
        assertNotNull(milestoneRepository.findOne(1L));
        assertThat(response.getHeaders().getLocation().getPath(), is("/milestones"));
    }
}
