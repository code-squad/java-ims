package codesquad.service;

import java.util.List;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;

@Service
public class IssueService {

	@Resource(name="issueRepository")
	private IssueRepository issueRepository;
	
	public Issue add(User loginUser, IssueDto issueDto) {
		Issue issue = issueDto.toIssue();
		issue.writeBy(loginUser);
		return issueRepository.save(issue);
	}

	public List<Issue> findAll() {
		return issueRepository.findAll();
	}

	public Issue findById(Long id) {
		return issueRepository.findById(id).orElseThrow(NullPointerException::new);
	}

	
	@Transactional
	public void update(User loginUser, Long id, IssueDto issueDto) throws AuthenticationException {
		Issue baseIssue = issueRepository.findById(id).orElseThrow(NullPointerException::new);
		baseIssue.update(loginUser,issueDto.toIssue());
	}

	public void delete(User loginUser, Long id) throws AuthenticationException {
		Issue issue = issueRepository.findById(id).orElseThrow(NullPointerException::new);
		issue.checkOwner(loginUser);
		issueRepository.delete(issue);
	}
	
}
