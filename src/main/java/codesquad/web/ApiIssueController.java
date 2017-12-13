package codesquad.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.model.Issue;
import codesquad.model.IssueRepository;
import codesquad.model.Milestone;
import codesquad.model.MilestoneRepository;
import codesquad.model.Reply;
import codesquad.model.ReplyRepository;
import codesquad.util.HttpSessionUtil;

@RestController
@RequestMapping("api")
public class ApiIssueController {
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private MilestoneRepository milestoneRepository;
	@Autowired
	private ReplyRepository replyRepository;

	@PutMapping("{id}/milestone/{milestoneId}")
	public Issue setMilestone(@PathVariable long id, @PathVariable long milestoneId) {
		Issue issue = issueRepository.findOne(id);
		Milestone milestone = milestoneRepository.findOne(milestoneId);
		issue.setMilestone(milestone);
		return issueRepository.save(issue);
	}
	
	@PostMapping("{id}/reply")
	public Reply writeReply(@PathVariable long id, Reply reply, HttpSession session) {
		Issue issue = issueRepository.findOne(id);
		reply.setWriter(HttpSessionUtil.loginSessionUser(session));
		reply.setIssue(issue);
		return replyRepository.save(reply);
	}
}
