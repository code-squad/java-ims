package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import javafx.scene.LightBase;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ApiLabelController {
    private static final Logger log = getLogger(ApiLabelController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @PostMapping("/api/labels")
    public ResponseEntity<Label> create(@LoginUser User user, @Valid @RequestBody Label label) {
        HttpHeaders headers = new HttpHeaders();
        Label newLabel = labelService.create(user, label);
        headers.setLocation(URI.create(String.valueOf(newLabel.getId())));

        return new ResponseEntity<Label>(newLabel, headers, HttpStatus.CREATED);
    }

    @GetMapping("/api/issues/{issueId}/labels")
    public List<Label> list() {
        return labelService.findAll();
    }

    @PostMapping("/api/issues/{issueId}/labels/{id}")
    public ResponseEntity<Issue> designate(@LoginUser User user, @PathVariable long issueId, @PathVariable long id) {
        return new ResponseEntity<Issue>(labelService.setLabel(issueId, id), HttpStatus.OK);
    }
}
