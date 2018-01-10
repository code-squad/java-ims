package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.IssueRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger Log = LoggerFactory.getLogger(UserAcceptanceTest.class);

	@Autowired
	private IssueRepository issueRepository;

	@Test
	public void createIssueFormPage() {
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		Log.debug("body : {}", response.getBody());
	}

	@Test
	public void createIssue_showList() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("title", "제목").addParameter("contents", "내용").build();
		ResponseEntity<String> response = template.postForEntity("/issues/", request, String.class);
		int numberOfIssue = issueRepository.findAll().size();
		assertThat(numberOfIssue, is(3)); // import.sql에 2개 더 있음.
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND)); // 리다이렉트 - 302
	}

	@Test
	public void showDetail() {
		ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(issueRepository.findOne((long) 1).getTitle(), is("11111"));
	}
}
