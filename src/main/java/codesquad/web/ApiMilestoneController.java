package codesquad.web;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;

@RestController
@RequestMapping("/api/milestones")
public class ApiMilestoneController {
	private static final Logger log = LoggerFactory.getLogger(ApiMilestoneController.class);

	@Resource(name = "issueService")
	private IssueService issueService;
	
	@Autowired
	MilestoneService milestoneService;

	@PostMapping("")
	public ResponseEntity<Void> create(@RequestBody Milestone milestone) {
		Milestone newMilestone = milestoneService.createMilestone(milestone);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(newMilestone.createUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public Milestone detail(@PathVariable long id, Model model) {
		return milestoneService.findByStoneId(id);
	}
	
	

}
