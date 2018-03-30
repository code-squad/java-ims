package codesquad.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.web.UserController;

@Service
public class IssueService {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource
	private IssueRepository issueRepository;

	public Issue add(User loginUser, IssueDto issue) {
		Issue newIssue = issue._toIssue();
		newIssue.writeBy(loginUser);
		return issueRepository.save(newIssue);
	}
	
	public Issue findById(long id) {
		return issueRepository.findOne(id);
	}
	
	public Issue update(User loginUser, long id, String newComment) throws UnAuthenticationException {
		Issue issue = issueRepository.findOne(id);
		Issue updatedIssue = issue.update(loginUser, newComment);
		return issueRepository.save(updatedIssue);
	}
	
	public void delete(User loginUser, long id) throws UnAuthenticationException {
		Issue issue = issueRepository.findOne(id);
		issue.delete(loginUser);
		issueRepository.save(issue);
	}

}
