package codesquad.service;

import codesquad.domain.*;
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

	@Resource(name = "milestoneRepository")
	private MilestoneRepository milestoneRepository;

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

	@Transactional
	public void register(long id, long milestoneId) throws IllegalArgumentException {
		Issue issue = issueRepository.findOne(id);
		Milestone milestone = milestoneRepository.findOne(milestoneId);

		if (issue == null || milestone == null) {
			throw new IllegalArgumentException();
		}

		issue.registerMilestone(milestone);
	}

	@Transactional
	public void attachFile(long id, Attachment attachment) throws IllegalArgumentException {
		Issue issue = issueRepository.findOne(id);
		issue.setAttachment(attachment);
	}
}
