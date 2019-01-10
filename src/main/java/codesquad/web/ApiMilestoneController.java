package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues/{id}/milestones")
public class ApiMilestoneController {

    private static final Logger logger = getLogger(ApiMilestoneController.class);

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private IssueService issueService;

    @GetMapping
    public List<Milestone> milestoneList(@PathVariable Long id) {
        logger.debug("Call milestone Method call");
        List<Milestone> response = new ArrayList<>();
        if(issueService.findIssue(id).isMilestone()) {
            response.add(issueService.findIssue(id).getMilestone());
        }

        if(!issueService.findIssue(id).isMilestone()) {
            response = milestoneService.findAllMilestone();
        }
        return response;
    }

    @PostMapping("/{milestoneId}")
    public ResponseEntity<String> registerMilestone(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long milestoneId) {
        Issue issue = issueService.findIssue(id);
        logger.debug("Call registerMilestone Method(), issue : {}", issue);
        milestoneService.registerMilestone(loginUser, issue, milestoneId);
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
