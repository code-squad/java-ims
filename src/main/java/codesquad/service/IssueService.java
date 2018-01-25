package codesquad.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import codesquad.InputDataNullException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Label;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;

@Service
public class IssueService {
	@Resource(name = "messageSourceAccessor")
	private MessageSourceAccessor msa;
	
	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;
	
	public Issue add(IssueDto issueDto) {
		if(issueDto == null) {
			throw new InputDataNullException(msa.getMessage("nullError")); 
		}
		return issueRepository.save(issueDto._toIssue());
	}
	
	public Issue findById(long id) {
		return  issueRepository.findOne(id);
	}
	
	public List<IssueDto> findAll() {
		List<IssueDto> issueDtoList = new ArrayList<>();
		for (Issue issue : issueRepository.findAll()) {
			issueDtoList.add(issue._toIssueDto());
		}
		return issueDtoList;
	}
	
	public Issue update(User loginUser, IssueDto issueDto, long id) throws Exception {
		Issue original = issueRepository.findOne(id);
		original.update(loginUser, issueDto);
		return issueRepository.save(original);
	}
	
	public Issue updateIssue(Issue issue) {
		return issueRepository.save(issue);
	}
	
	public void delete(long id) {
		issueRepository.delete(id);
	}
	
	public Issue addLabel(Long id, Label label) {
		Issue issue = findById(id);
		issue.addLabelIntoLabels(label);
		return updateIssue(issue);
	}
	
	public Issue addMilestone(Long id, Milestone milestone) {
		Issue issue = findById(id);
		issue.addMilesstone(milestone);
		return updateIssue(issue);
	}
	
	public Issue addUser(Long id, User user) {
		Issue issue = findById(id);
		issue.addUser(user);
		return updateIssue(issue);
	}
}
