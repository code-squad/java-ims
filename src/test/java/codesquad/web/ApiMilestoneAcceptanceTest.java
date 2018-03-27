package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.service.IssueService;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class ApiMilestoneAcceptanceTest extends AcceptanceTest {

	private static final Logger log = LoggerFactory.getLogger(ApiMilestoneAcceptanceTest.class);

	@Autowired
	IssueService issueService;
	
	@Test
	public void create() throws Exception {
		Milestone milestone = createTestMilestone("create title");
		String location = createResource("/api/milestones", milestone);

		Long id = Optional.ofNullable(findMilestoneId("create title"))
				.orElseThrow(() -> new MyExeption("찾는 마일스톤이 없습니다"));
		Milestone dbMilestone = getResource(location, Milestone.class, findDefaultUser());
		log.info("dbmilestone : {}", dbMilestone);
		log.info("milestone : {}", milestone);
		assertTrue(milestone.getTitle().equals(dbMilestone.getTitle()));
	}

}
