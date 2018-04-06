package codesquad.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.web.IssueController;

@Service
public class IssueService {
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);

	@Resource
	private IssueRepository issueRepository;
	
	@Resource
	private MilestoneService milestoneService;

	public Issue add(User loginUser, IssueDto issue) {
		log.debug("Issue service (add) in");
		Issue newIssue = issue._toIssue();
		newIssue.writeBy(loginUser);
		return issueRepository.save(newIssue);
	}
	
	public Issue findById(long id) {
		log.debug("Issue service (find by id) in");
		return issueRepository.findOne(id);
	}
	
	@Transactional
	public Issue update(User loginUser, long id, String newComment) throws UnAuthenticationException {
		log.debug("Issue service (update) in");
		Issue issue = issueRepository.findOne(id);
		return issue.update(loginUser, newComment);
	}
	
	@Transactional
	public void delete(User loginUser, long id) throws UnAuthenticationException {
		log.debug("Issue service (delete) in");
		Issue issue = issueRepository.findOne(id);
		issue.delete(loginUser);
	}
	
	@Transactional
	public void registerMilestone(long issueId, long milestoneId) {
		Issue issue = issueRepository.findOne(issueId);
		issue.registerMilestone(milestoneService.addIssueThenReturnMilestone(issue, milestoneId));
	}

}
