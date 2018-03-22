package codesquad.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;

@Service
public class IssueService {
	
	@Resource(name = "issueRepository")
	IssueRepository issueRepository;
	
	
	public Issue findById(Long id) {
		return issueRepository.findOne(id);
	}
	
	public Iterable<Issue> findAll(){
		return issueRepository.findByDeleted(false);
	}

	public void create(User loginUser, IssueDto issueDto) {
		Issue newIssue = new Issue(issueDto.getTitle(), issueDto.getContents());
		newIssue.writeBy(loginUser);
		issueRepository.save(newIssue);
		
	}
	
	public void update(User loginUser, long id, IssueDto issueDto) {
		Issue oldIssue = findById(id);
		oldIssue.update(loginUser, issueDto);
		issueRepository.save(oldIssue);
	}

	public void delete(User loginUser, long id) {
		issueRepository.delete(findById(id).delete(loginUser));
		
	}

}
