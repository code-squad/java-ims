package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/issue/{issueId}/answers")
public class ApiAnswerController {

    @Resource(name = "issueService")
    IssueService issueService;

    @PostMapping("")
    public ResponseEntity<Answer> create(@LoginUser User loginUser, @PathVariable long issueId, String comment) {
        Answer savedAnswer = issueService.addAnswer(loginUser,issueId,comment);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/issue/" + issueId + "/answers/" + savedAnswer.getId()));
        return new ResponseEntity<>(savedAnswer,headers, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Answer show(@PathVariable long id) {
        return issueService.findAnswerById(id);
    }

    @PutMapping("{id}")
    public Answer update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody String comment) {
        return issueService.updateAnswer(loginUser, id, comment);
    }

}
