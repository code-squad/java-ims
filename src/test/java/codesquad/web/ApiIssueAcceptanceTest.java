package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.MileStone;
import codesquad.domain.MileStoneRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.service.IssueService;
import support.test.AcceptanceTest;

public class ApiIssueAcceptanceTest extends AcceptanceTest {

	@Resource(name = "issueService")
	private IssueService issueService;
	
	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;
	
	@Resource(name = "mileStoneRepository")
	private MileStoneRepository mileStoneRepository;
	
	@Resource(name = "userRepository")
	private UserRepository userRepository;
	
	@Resource(name = "labelRepository")
	private LabelRepository labelRepository;
	
	@Test
	public void set_Milestone() throws Exception {
		// set milstone 을 통해 db에 저장된 issue 와 요청으로 보낸 issue가 일치하는가를 test.
		User user = findDefaultUser();
		Issue issue = new Issue(1L, user, "subject", "comment");
		issue = issueRepository.save(issue);
		// 응답코드 비교.
		// 리소스를 생성해 response를 반환한다.
		MileStone milestone = new MileStone(1L, "subject", "startDate", "endDate");
		milestone = mileStoneRepository.save(milestone);
		
		String location = String.format("/api/issues/%d/milestones/%d", issue.getId(), milestone.getId());
		basicAuthTemplate().put(location, null);
		Issue dbissue = issueRepository.findOne(issue.getId());
		MileStone setMilestone = dbissue.getMileStone();
		assertThat(setMilestone, is(milestone));	
		issueRepository.delete(issue);
		mileStoneRepository.delete(milestone);
	}
	
	@Test
	public void set_AssignUser() throws Exception {
		User user = findDefaultUser();
		Issue issue = new Issue(1L, user, "subject", "comment");
		issue = issueRepository.save(issue);
		
		User assignedUser = new User((long)3L, "chloe", "password", "chloe");
		assignedUser = userRepository.save(assignedUser);
		String location = String.format("/api/issues/%d/users/%d", issue.getId(), assignedUser.getId());
		basicAuthTemplate().put(location, null);
		Issue dbissue = issueRepository.findOne(issue.getId());
		User dbAssignedUser = dbissue.getAssignedUser();
		assertThat(dbAssignedUser, is(assignedUser));	
		
		issueRepository.delete(issue);
		userRepository.delete(assignedUser);
	}
	// 트랜잭션이 없는 상태에서는 default 로 lazy loading 이 허용되지 않는다.
	@Test
	public void set_Label() throws Exception {
		// login한 상태의 유저.
		User issueWriter = findDefaultUser();
		
		Issue issue = new Issue(1L, issueWriter, "subject", "comment");
		issue = issueRepository.save(issue);
		
		Label label = new Label(1L, "subject");
		label = labelRepository.save(label);
		
		String location = String.format("/api/issues/%d/labels/%d", issue.getId(), label.getId());
		basicAuthTemplate().put(location, null);
		Issue dbissue = issueRepository.findOne(issue.getId());
		assertTrue(dbissue.getLabels().contains(label));		
//		issueRepository.delete(issue);
//		labelRepository.delete(label);
	}
}
