package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/issues/{issueId}/answers")
public class ApiAnswerController {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Resource(name = "answerService")
    private AnswerService answerService;

    @Resource(name = "issueService")
    private IssueService issueService;

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody AnswerDto answerDto, @PathVariable long issueId, @LoginUser User user) throws Exception {
        log.debug("issue id : {}", issueId);
        Answer saveAnswer = issueService.addAnswer(user, issueId, answerDto.toAnswer());
        log.debug("saveAnswer : {}", saveAnswer);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(saveAnswer.generateUrl()));
        log.debug("setLocation : {}", URI.create(saveAnswer.generateUrl()));

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("{answerId}")
    public AnswerDto get(@PathVariable long issueId, @PathVariable long answerId) throws Exception {
        Answer answer = issueService.findAnswerById(answerId);
        log.debug("answer from repository : {}", answer);

        return answer.toAnswerDto();
    }
}
