package codesquad.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import codesquad.UnAuthenticationException;
import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.ServerPath;
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
	private AttachmentRepository attachmentRepository;

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
	public void updateLabel(User loginUser, long id, long labelId) throws UnAuthenticationException {
		log.debug("issue service(updateLabel) in");
		Issue issue = issueRepository.findOne(id);
		try {
			issue.updateLabel(loginUser, labelService.findOne(labelId));
			Answer newAnswer = issue.updateLabelThenMakeComment(loginUser);
			answerRepository.save(newAnswer);
		} catch (UnAuthenticationException e) {
			e.printStackTrace();
			throw new UnAuthenticationException();
		}
	}
	
	@Transactional
	public Answer addComments(User loginUser, long id, String comment) {
		log.debug("issue service(addComments) in");
		Issue issue = issueRepository.findOne(id);
		Answer addedAnswer = issue.addComment(loginUser, comment);
		return answerRepository.save(addedAnswer);
	}
	
	@Transactional
	public Attachment uploadFile(long id, MultipartFile file) throws IllegalStateException, IOException {
		long time = System.currentTimeMillis(); 
		
		ServerPath serverPath = new ServerPath();
		File fileToCheckIO = new File(serverPath.getServerPath() + file.getOriginalFilename());
		File dbFile = new File(serverPath.getServerPath() + time);
		
		file.transferTo(fileToCheckIO);
		fileToCheckIO.renameTo(dbFile);
		
		Attachment uploadedFile = new Attachment(file.getOriginalFilename(), dbFile.getName(), dbFile.getAbsolutePath(), file.getContentType());
		attachmentRepository.save(uploadedFile);

		Issue issue = issueRepository.findOne(id);
		issue.addAttachment(uploadedFile);

		return uploadedFile;
	}
}
