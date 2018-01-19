package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Label;
import codesquad.domain.MileStone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;

@Service
public class IssueService {
	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;

	public Issue add(IssueDto issueDto, @LoginUser User loginUser) {
		// issue 객체로 바꾸어 집어넣는다.
		issueDto.setUser(loginUser);
		return issueRepository.save(issueDto._toIssue());
	}

	public Issue findById(long id) {
		return issueRepository.findOne(id);
	}

	public List<Issue> findAll() {
		return issueRepository.findAll();
	}

	public Issue update(@LoginUser User loginUser, long id, String subject, String comment) {
		Issue issue = issueRepository.findOne(id);
		issue.update(loginUser, subject, comment);
		return issueRepository.save(issue);
	}

	public void delete(@LoginUser User loginUser, long id) {
		Issue issue = issueRepository.findOne(id);
		if (!issue.isSameUser(loginUser)) {
			throw new UnAuthorizedException();
		}
		issueRepository.delete(issue);
	}

	public Issue setMileStone(@LoginUser User loginUser, Issue issue, MileStone mileStone) {
		if (!issue.isSameUser(loginUser)) {
			throw new UnAuthorizedException();
		}
		issue.setMileStone(mileStone);
		return issueRepository.save(issue);
	}

	public Issue setAssignedUser(@LoginUser User loginUser, Issue issue, User user) {
		if (!issue.isSameUser(loginUser)) {
			throw new UnAuthorizedException();
		}
		issue.setAssignedUser(user);
		return issueRepository.save(issue);
	}

	public Issue setLabel(@LoginUser User loginUser, Issue issue, Label label) {
		if (!issue.isSameUser(loginUser)) {
			throw new UnAuthorizedException();
		}
		issue.setLabel(label);
		return issueRepository.save(issue);
	}

	public void addLabel(Label label, Issue issue) {
		issue.addLabel(label);
	}

}
