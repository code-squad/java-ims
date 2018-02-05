package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IssueService {

	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;

	public Issue add(User loginUser, Issue issue) {
		issue.writeBy(loginUser);
		return issueRepository.save(issue);
	}

	public List<Issue> findAll() {
		return issueRepository.findAll();
	}

	public Issue findById(long id) {
		return issueRepository.findOne(id);
	}

	public void delete(User loginUser, long id) {
		Issue issue = issueRepository.findOne(id);

		if (!issue.isOwner(loginUser))
			throw new UnAuthorizedException();

		issueRepository.delete(id);
	}

	@Transactional
	public void update(User user, long id, IssueDto updateIssue) {
		Issue issue = issueRepository.findOne(id);
		issue.update(user, updateIssue.toIssue());
	}
}
