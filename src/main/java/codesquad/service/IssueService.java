package codesquad.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
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

	public void create(IssueDto issueDto) {
		issueRepository.save(new Issue(issueDto.getTitle(), issueDto.getContents()));
		
	}

}
