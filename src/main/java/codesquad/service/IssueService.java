package codesquad.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;

@Service
public class IssueService {
	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;
	
	public Issue add(IssueDto issueDto) {
		return issueRepository.save(issueDto._toIssue());
	}
	
	public Issue findById(long id) {
		return issueRepository.findOne(id);
	}
	
	public Issue update(IssueDto issueDto, long id) {
		Issue original = issueRepository.findOne(id);
		original.update(issueDto);
		return issueRepository.save(original);
	}
	
	public Issue update(Issue issue) {
		return issueRepository.save(issue);
	}
	
	public void delete(long id) {
		issueRepository.delete(id);
	}
}
