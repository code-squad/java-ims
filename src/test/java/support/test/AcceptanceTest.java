package support.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    private static final String DEFAULT_LOGIN_USER = "ksm0814";

    @Autowired
    protected TestRestTemplate template;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AnswerRepository answerRepository;
    
    @Autowired
    private IssueService issueService;
    
    @Autowired
    private MilestoneService milestoneService;
    
    @Autowired
    private LabelService labelService;;
    
    public TestRestTemplate template() {
        return template;
    } 
    
    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(findDefaultUser());
    }
    
    public TestRestTemplate basicAuthTemplate(User loginUser) {
        return template.withBasicAuth(loginUser.getUserId(), loginUser.getPassword());
    }
    
    protected User findDefaultUser() {
        return findByUserId(DEFAULT_LOGIN_USER);
    }
    
    protected User findByUserId(String userId) {
        return userRepository.findByUserId(userId).get();
    }
    
    protected Long findAnswerId(String contents) {
    	for (Answer answer : answerRepository.findAll()) {
			if(answer.getContents().equals(contents))
				return answer.getId();
		}
    	return null;
    }
    
    public Milestone createTestMilestone(String title) throws ParseException {
		return new Milestone(title, "2018-03-23T11:51", "2018-03-23T11:59");
	}
    
    public ResponseEntity<String> createTestLabel(TestRestTemplate myTemplate, String title, String color){
		HtmlFormDataBuilder dataBuilder = HtmlFormDataBuilder.urlEncodedForm().addParameter("title",title)
				.addParameter("color", color);
		HttpEntity<MultiValueMap<String, Object>> request = dataBuilder.build();
		return myTemplate.postForEntity("/labels", request, String.class);
	}
    
    
    public Long findMilestoneId(String title) {
		for (Milestone milestone : milestoneService.findStoneAll()) {
			if (milestone.getTitle().equals(title))
				return milestone.getId();
		}
		return null;
	}
	
	public Long findIssueId(String title) {
		for (Issue issue : issueService.findAll()) {
			if(issue.getTitle().equals(title))
				return issue.getId();
		}
		return null;
	}
	
	public Long findLabelId(String title) {
		for(Label label : labelService.findLabelAll()) {
			if(label.getTitle().equals(title))
				return label.getId();
		}
		return null;
	}

    
    protected String createResource(String path, Object bodyPayload) {
        ResponseEntity<String> response = basicAuthTemplate().postForEntity(path, bodyPayload, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        return response.getHeaders().getLocation().getPath();
    }
    
    protected <T> T getResource(String location, Class<T> responseType, User loginUser) {
        return basicAuthTemplate(loginUser).getForObject(location, responseType);
    }
    
    protected ResponseEntity<String> getResource(String location, User loginUser) {
        return basicAuthTemplate(loginUser).getForEntity(location, String.class);
    }
}
