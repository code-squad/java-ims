package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/api/issue/{issueId}/answers")
public class ApiAnswerController {
    private static final Logger log =  LoggerFactory.getLogger(ApiAnswerController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @PostMapping("")
    public ResponseEntity<Answer> create(@PathVariable long issueId, @LoginUser User loginUser, String comment) {
        log.info("restcontroller called");
        Answer answer = issueService.addAnswer(issueId, loginUser, comment);
        log.info("answer : {}", answer.toString());
        HttpHeaders headers = new HttpHeaders();
        log.info("addAnswer getId : {}", answer.getId());
        headers.setLocation(URI.create(String.format("/api/issue/%d/answers/%d", issueId, answer.getId())));
        return new ResponseEntity<Answer>(answer, headers, HttpStatus.CREATED);
    }
}
