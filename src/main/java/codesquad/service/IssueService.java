package codesquad.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.IssueDto;

@Service
public class IssueService {

	@Resource(name = "issueRepository")
	IssueRepository issueRepository;
	
	@Resource(name = "milestoneRepository")
	MilestoneRepository milestoneRepository;
	
	@Resource(name = "userRepository")
	UserRepository userRepository;
	
	@Resource(name = "labelRepository")
	LabelRepository labelRepository;

	public Issue findById(Long id) {
		return issueRepository.findOne(id);
	}

	public Iterable<Issue> findAll() {
		return issueRepository.findByDeleted(false);
	}
	
	public Milestone findByStoneId(Long id){
		return milestoneRepository.findOne(id);
	}
	
	public Iterable<Milestone> findStoneAll(){
		return milestoneRepository.findAll();
	}
	
	public User findByUserId(long id) {
		return userRepository.findOne(id);
	}
	
	public Iterable<Label> findLabelAll(){
		return labelRepository.findAll();
	}
	
	public Label findByLabelId(Long id) {
		return labelRepository.findOne(id);
	}

	public void create(User loginUser, IssueDto issueDto) {
		Issue newIssue = new Issue(issueDto.getTitle(), issueDto.getContents());
		newIssue.writeBy(loginUser);
		issueRepository.save(newIssue);

	}

	@Transactional
	public void update(User loginUser, long id, IssueDto issueDto) {
		Issue oldIssue = findById(id);
		oldIssue.update(loginUser, issueDto);
	}
	
	@Transactional
	public void delete(User loginUser, long id) {
		findById(id).delete(loginUser);
	}
	
	public Milestone createMilestone(Milestone milestone) {
		if(milestone.equals(null))
			throw new IllegalStateException();
		return milestoneRepository.save(milestone);
	}
	@Transactional
	public void addMilestone(long id, long milestoneId) {
		Issue issue = findById(id);
		issue.setMilestone(findByStoneId(milestoneId));
	}
	
	@Transactional
	public void addAssignee(long id, long userId) {
		Issue issue = findById(id);
		issue.setAssignee(findByUserId(userId));
	}
	
	@Transactional
	public void addLabel(long id, long labelId) {
		Issue issue = findById(id);
		issue.setLabel(findByLabelId(labelId));
	}
	

}
