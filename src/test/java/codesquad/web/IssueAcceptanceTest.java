package codesquad.web;

import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

	@Autowired
	private IssueRepository issueRepository;

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void create() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("title", "issueTitle")
				.addParameter("contents", "issueContents").build();

		ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
	}

	@Test
	public void showAll() {
	}

	@Test
	public void show() {
	}

}
