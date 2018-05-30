package codesquad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;

@Service
public class IssueService {

	@Resource(name="issueRepository")
	private IssueRepository issueRepository;
	
	public Issue add(IssueDto issueDto) {
		return issueRepository.save(issueDto.toIssue());
	}

	public List<Issue> findAll() {
		return issueRepository.findAll();
	}

	public Issue findById(Long id) {
		return issueRepository.findById(id).orElseThrow(NullPointerException::new);
	}
	
}
