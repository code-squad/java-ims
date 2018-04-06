package codesquad.web;

import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/labels")
public class APILabelController {

    @Autowired
    LabelService labelService;

    @Autowired
    IssueService issueService;

    @PostMapping("/{labelId}/issues/{id}")
    public Label addLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        return labelService.addLabel(issueService.findById(id), labelId);
    }

}
