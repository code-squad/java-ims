package codesquad.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.UnAuthenticationException;
import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
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
	
	@Resource
	private AnswerRepository answerRepository;

	@Resource
	private UserService userService;

	@Resource
	private LabelService labelService;

	public Issue add(User loginUser, IssueDto issue) {
		log.debug("Issue service (add) in");
		Issue newIssue = issue._toIssue();
		return issueRepository.save(newIssue.writeBy(loginUser));
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
	public void registerMilestone(long issueId, long milestoneId, User loginUser) {
		log.debug("issueService (registerMilestone) in");
		Issue issue = issueRepository.findOne(issueId);
		issue.registerMilestone(milestoneService.findById(milestoneId));
		Answer newAnswer = issue.addCommentsThatRegisteredMilestone(loginUser);
		answerRepository.save(newAnswer);
	}
	
	@Transactional
	public void makeManager(long id, long userId, User loginUser) throws UnAuthenticationException {
		log.debug("issue service(makeManager) in");
		Issue issue = issueRepository.findOne(id);
		if (issue.isManager(loginUser) || issue.isOwner(loginUser)) {
			User selectedUser = userService.findById(loginUser, userId);
			issue.managedBy(selectedUser);
			return;
		}
		throw new UnAuthenticationException();
	}

	@Transactional
	public void updateLabel(User loginUser, long id, long labelId) {
		log.debug("issue service(updateLabel) in");
		Issue issue = issueRepository.findOne(id);
		issue.updateLabel(loginUser, labelService.findOne(labelId));
	}
	
	@Transactional
	public void addComments(String comment) {
		log.debug("issue service(addComments) in");
		
	}

}
