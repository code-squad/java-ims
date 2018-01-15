package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
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
		if(!issue.isSameUser(loginUser)) {
			throw new UnAuthorizedException();
		}
		issueRepository.delete(issue);
	}
}
