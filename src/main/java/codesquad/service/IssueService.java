package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IssueService {
	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;

	public Issue add(IssueDto issueDto, User loginUser) {
		return issueRepository.save(issueDto._toIssue(loginUser));
	}

	public List<Issue> findAll() {
		return issueRepository.findAll();
	}

	public Issue findById(long issueId) {
		return issueRepository.findOne(issueId);
	}

	@Transactional
	public Issue update(User loginUser, long id, IssueDto updatedIssue) {
		Issue issue = issueRepository.findOne(id);
		issue.update(loginUser, updatedIssue);

		return issue;
	}

	public boolean delete(User loginUser, long id) {
		Issue issue = issueRepository.findOne(id);
		if (!issue.isOwner(loginUser)) {
			return false;
		}

		issueRepository.delete(id);
		return true;
	}
}
