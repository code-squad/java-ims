package codesquad.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.service.IssueService;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
	@Resource(name = "issueService")
	private IssueService issueService;
	
}
