package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/issues/{id}/labels")
public class ApiLabelController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private LabelService labelService;

    @GetMapping()
    public List<Label> list(@PathVariable Long id) {
        List<Label> labels = new ArrayList<>();
        if(issueService.findIssue(id).isLabel()) {
            labels.add(issueService.findIssue(id).getLabel());
        }

        if(!issueService.findIssue(id).isLabel()) {
            labels = labelService.findAll();
        }

        return labels;
    }

    @PostMapping("/{labelId}")
    public ResponseEntity<String> registerLabel(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long labelId) throws UnAuthenticationException {
        Issue issue = issueService.findIssue(id);
        labelService.registerLabel(loginUser, issue, labelId);
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
