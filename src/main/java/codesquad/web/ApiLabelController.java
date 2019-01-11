package codesquad.web;

import codesquad.domain.label.Label;
import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issue/{id}/label")
public class ApiLabelController {
    private static final Logger log = getLogger(ApiLabelController.class);

    @Autowired
    private LabelService labelService;

    @Autowired
    private IssueService issueService;

    @GetMapping("")
    public List<Label> show() {
        log.debug("label show");
        return labelService.findAll();
    }

    @PostMapping("{labelId}")
    public ResponseEntity<Void> register(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        log.debug("이슈에 라벨 적용");
        issueService.registerLabel(labelService.findById(labelId), id, loginUser);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
