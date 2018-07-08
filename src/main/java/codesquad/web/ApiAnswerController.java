package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import codesquad.dto.AnswersDto;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/issues/{issueId}/answers")
public class ApiAnswerController {
    @Resource(name = "answerService")
    AnswerService answerService;

    @PostMapping("")
    public ResponseEntity<AnswerDto> create(@LoginUser User loginUser, @PathVariable Long issueId, @RequestBody Map<String, String> data) {
        String content = data.get("content");
        Answer result = answerService.addAnswer(loginUser, issueId, content);
        AnswerDto resultDto = new AnswerDto(result.getContents(), result.getWriter().getName(),
                result.getFormattedCreateDate(), result.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/issues/" + issueId + "/answers/" + result.getId()));

        return new ResponseEntity<>(resultDto, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<String> delete(@LoginUser User loginUser, @PathVariable Long answerId) {
        answerService.deleteAnswer(loginUser, answerId);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<AnswersDto> list() {
        AnswersDto answers = answerService.getAnswers();
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
}
