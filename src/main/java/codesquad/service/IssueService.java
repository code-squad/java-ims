package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;

@Service
public class IssueService {
	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;

	public void add(IssueDto issueDto) {
		issueRepository.save(issueDto._toIssue());
	}

	public List<Issue> list() {
		return issueRepository.findAll();
	}

	public Object findIssue(long id) {
		return issueRepository.findOne(id);
	}

	public void delete(long id, User loginUser) {
		Issue issue = issueRepository.findOne(id);
		if (issue.matchWriter(loginUser)) {
			issueRepository.delete(id);
		}
	}

	public void update(long id, IssueDto issueDto, User loginUser) {
		Issue originIssue = issueRepository.findOne(id);
		if (originIssue.matchWriter(loginUser)) {
			originIssue.update(issueDto._toIssue());
			issueRepository.save(originIssue);
		}
	}

	public void setMilestone(long iId, Milestone milestone, User loginUser) {
		Issue issue = issueRepository.findOne(iId);
		if (issue.matchWriter(loginUser)) {
			issue.setMilestone(milestone);
			issueRepository.save(issue);
		}
	}

}
