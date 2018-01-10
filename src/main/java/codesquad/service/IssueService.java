package codesquad.service;

import java.util.List;

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
		// issue 객체로 바꾸어 집어넣는다.
		return issueRepository.save(issueDto._toIssue());
	}

	public Issue findById(long id) {
		return issueRepository.findOne(id);
	}

	public List<Issue> findAll() {
		return issueRepository.findAll();
	}
	
	
}
