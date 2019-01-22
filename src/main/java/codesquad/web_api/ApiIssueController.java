package codesquad.web_api;

import codesquad.domain.*;
import codesquad.dto.AnswerDto;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = getLogger(ApiIssueController.class);

    @Resource (name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @Valid @RequestBody IssueDto issue) {
        Issue savedIssue = issueService.create(loginUser, issue);
        log.debug("saveIssue:{}", savedIssue);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/issues/" + savedIssue.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public IssueDto show(@PathVariable long id) {
        log.debug("findIssue:{}", issueService.findByIssueId(id)._toIssueDto());
        return issueService.findByIssueId(id)._toIssueDto();
    }

    @GetMapping("/{id}/milestone")
    public List<Milestone> list(@PathVariable long id) {
        return milestoneService.findAll();
    }

    @GetMapping("/{id}/labels")
    public List<Label> label(@LoginUser User loginUser, @PathVariable long id) {
        return labelService.findAll();
    }

    @GetMapping("/{id}/milestone/{milestoneId}")
    public Map<String, Object> milestoneChoice(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        Issue issue = issueService.findByIssueId(id);
        Map<String, Object> dtos = new HashMap<>();
        dtos.put("milestone", milestoneService.addIssue(loginUser, milestoneId, issue));
        dtos.put("user", issue._toIssueDto().getWriter().getUserId());
        return dtos;
    }

    @GetMapping("/{id}/assignee/{userId}")
    public Set<User> assign(@LoginUser User loginUser , @PathVariable long id, @PathVariable long userId) {
        User assignee = userService.findById(id);
        return issueService.assignee(loginUser, id, assignee);
    }

    @PostMapping("/{id}/answers")
    public Answer create(@LoginUser User loginUser, @PathVariable long id, AnswerDto answerDto) {
        return issueService.addAnswer(loginUser, id, answerDto);
    }

    @GetMapping("/{id}/answers/{answerId}")
    public Answer modifyForm(@LoginUser User loginUser, @PathVariable long answerId) {
        Answer answer = issueService.findByAnswerId(answerId);
        answer.isOwner(loginUser);
        return answer;
    }

    @PutMapping("/{id}/answers/{answerId}")
    public Answer modify(@LoginUser User loginUser, @PathVariable long answerId, AnswerDto answerDto) {
        return issueService.modifyAnswer(loginUser, answerId, answerDto);
    }

    @DeleteMapping("/{id}/answers/{answerId}")
    public void delete(@LoginUser User loginUser, @PathVariable long answerId) {
        issueService.deleteAnswer(loginUser, answerId);
    }
}
