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

	public Issue findById(Long id) {
		return issueRepository.findOne(id);
	}

	public Iterable<Issue> findAll() {
		return issueRepository.findByDeleted(false);
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
	

}
