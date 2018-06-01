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

import codesquad.domain.MileStoneRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class MileStoneAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(MileStoneAcceptanceTest.class);

	@Autowired
	private MileStoneRepository mileStoneRepository;

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/milestone/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void create() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "제목제목").addParameter("startDate", "2018-06-30T14:03")
				.addParameter("endDate", "2018-07-12T16:02").build();
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestone", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(mileStoneRepository.findById(1L).isPresent(), is(true));
		assertThat(response.getHeaders().getLocation().getPath(), is("/milestone"));
	}

	@Test
	public void create_fail() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "제목제목").addParameter("startDate", "2018-06-30T14:03")
				.addParameter("endDate", "2018-07-12T16:02").build();
		ResponseEntity<String> response = template.postForEntity("/milestone", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
		
	}


}
