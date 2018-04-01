package codesquad.service;

import java.util.ArrayList;
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

	public void add(IssueDto issueDto) {
		issueRepository.save(issueDto._toIssue());
	}

	public List<IssueDto> list() {
		List<IssueDto> issues = new ArrayList<>();
		for(Issue issue : issueRepository.findAll()) {
			issues.add(issue._toIssueDto());
		}
		return issues;
	}

}
