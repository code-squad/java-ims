package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

	public void update(Long id, IssueDto issueDto) {
		Issue baseIssue = issueRepository.findById(id).orElseThrow(NullPointerException::new);
		baseIssue.update(issueDto.toIssue());
		issueRepository.save(baseIssue);
	}

	public void delete(Long id) {
		Issue issue = issueRepository.findById(id).orElseThrow(NullPointerException::new);
		issueRepository.delete(issue);
	}
	
}
