package codesquad.service;

import java.util.List;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.MileStone;
import codesquad.domain.MileStoneRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.IssueDto;

@Service
public class IssueService {

	
	@Resource(name ="userRepository")
	private UserRepository userRepository;
	
	@Resource(name="issueRepository")
	private IssueRepository issueRepository;
	
	@Resource(name="mileStoneRepository")
	private MileStoneRepository mileStoneRepository;
	
	public Issue add(User loginUser, IssueDto issueDto) {
		Issue issue = issueDto.toIssue();
		issue.writeBy(loginUser);
		return issueRepository.save(issue);
	}

	public List<Issue> findAll() {
		return issueRepository.findAll();
	}

	public Issue findById(Long id) {
		return issueRepository.findById(id).orElseThrow(()->new NullPointerException("존재하지 않는 이슈"));
	}

	
	@Transactional
	public void update(User loginUser, Long id, IssueDto issueDto) throws AuthenticationException {
		Issue baseIssue = findById(id);
		baseIssue.update(loginUser,issueDto.toIssue());
	}

	public void delete(User loginUser, Long id) throws AuthenticationException {
		Issue issue = findById(id);
		issue.checkOwner(loginUser);
		issueRepository.delete(issue);
	}

	@Transactional
	public void setMileStone(User loginUser, Long id, Long mileStoneId) throws AuthenticationException {
		Issue issue = findById(id);
		MileStone mileStone = mileStoneRepository.findById(mileStoneId).orElseThrow(NullPointerException::new);
		issue.setMileStone(loginUser, mileStone);
	}

	@Transactional
	public void setAssignee(User loginUser, Long id, Long userId) throws AuthenticationException {
		Issue issue = findById(id);
		User assignee = userRepository.findById(userId).orElseThrow(NullPointerException::new);
		issue.setAssignee(loginUser, assignee);
	}
	
	
}
