package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.UnAuthenticationException;
import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.MileStone;
import codesquad.domain.MileStoneRepository;
import codesquad.domain.Result;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.IssueDto;

@Service
public class IssueService {

	
	@Resource(name ="userRepository")
	private UserRepository userRepository;
	
	@Resource(name="issueRepository")
	private IssueRepository issueRepository;
	
	@Resource(name="mileStoneRepository")
	private MileStoneRepository mileStoneRepository;
	
	@Resource(name="labelRepository")
	private LabelRepository labelRepository;
	
	@Resource(name="answerRepository")
	private AnswerRepository answerRepository;
	
	public Issue add(User loginUser, IssueDto issueDto) {
		Issue issue = issueDto.toIssue();
		issue.writeBy(loginUser);
		return issueRepository.save(issue);
	}

	public List<Issue> findAll() {
		return issueRepository.findAll();
	}

	public Issue findById(Long id) {
		return issueRepository.findById(id).orElseThrow(()->new NullPointerException("존재하지 않는 이슈"));
	}

	public List<Label> findAllLabels() {
		return labelRepository.findAll();
	}

	
	@Transactional
	public void update(User loginUser, Long id, IssueDto issueDto) throws UnAuthenticationException {
		Issue baseIssue = findById(id);
		baseIssue.update(loginUser,issueDto.toIssue());
	}

	public void delete(User loginUser, Long id) throws UnAuthenticationException {
		Issue issue = findById(id);
		issue.checkOwner(loginUser);
		issueRepository.delete(issue);
	}

	@Transactional
	public void putInMileStone(User loginUser, Long id, Long mileStoneId) throws UnAuthenticationException {
		Issue issue = findById(id);
		MileStone mileStone = mileStoneRepository.findById(mileStoneId).orElseThrow(NullPointerException::new);
		issue.putInMileStone(loginUser, mileStone);
	}

	@Transactional
	public void appointAssignee(User loginUser, Long id, Long userId) throws UnAuthenticationException {
		Issue issue = findById(id);
		User assignee = userRepository.findById(userId).orElseThrow(NullPointerException::new);
		issue.appointAssignee(loginUser, assignee);
	}
	
	@Transactional
	public void addLabel(User loginUser, Long id, Long labelId) throws UnAuthenticationException {
		Issue issue = findById(id);
		Label label = labelRepository.findById(labelId).orElseThrow(NullPointerException::new);
		issue.addLabel(loginUser, label);
	}

	public Answer addAnswer(User loginUser, Long issueId, String comment) {
		Issue issue = findById(issueId);
		return answerRepository.save(new Answer(loginUser, comment, issue));
	}

	public void deleteAnswer(User loginUser, Long answerId) throws UnAuthenticationException {
		Answer answer = answerRepository.findById(answerId).orElseThrow(NullPointerException::new);
		answer.checkOwner(loginUser);
		answerRepository.delete(answer);
	}
	
	@Transactional
	public Answer updateAnswer(User loginUser, Long answerId, String comment) throws UnAuthenticationException {
		Answer answer = answerRepository.findById(answerId).orElseThrow(NullPointerException::new);
		answer.update(loginUser, comment);
		return answer;
	}

	public Result checkAnswerOwner(User loginUser, Long answerId) throws UnAuthenticationException {
		return answerRepository.findById(answerId).orElseThrow(NullPointerException::new).checkOwnerResult(loginUser);
	}

	
}
