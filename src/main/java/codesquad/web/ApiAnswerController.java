package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.DeleteHistory;
import codesquad.domain.ImageFile;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.ImageFileService;
import codesquad.service.IssueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/issue/{issueId}/answers")
public class ApiAnswerController {
    private static final Logger log = LogManager.getLogger(ApiAnswerController.class);

    @Resource(name = "issueService")
    IssueService issueService;

    @Resource(name = "imageFileService")
    private ImageFileService imageFileService;

    @PostMapping("")
    public ResponseEntity<Answer> create(@LoginUser User loginUser, @PathVariable long issueId, String comment) {
        Answer savedAnswer = issueService.addAnswer(loginUser,issueId,comment);
        log.debug("이슈 데이터 : {}", savedAnswer);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/issue/" + issueId + "/answers/" + savedAnswer.getId()));
        return new ResponseEntity<>(savedAnswer,headers, HttpStatus.CREATED);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<Answer> create(@LoginUser User loginUser, @PathVariable long issueId,
                                         @RequestParam(value = "file") MultipartFile file) throws IOException {
        ImageFile imageFile = imageFileService.add(file);
        Answer savedAnswer = issueService.addFileAnswer(loginUser,issueId, imageFile);
        log.debug("파일업로드");
        return new ResponseEntity<>(savedAnswer, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Answer show(@PathVariable long id) {
        return issueService.findAnswerById(id);
    }

    @PutMapping("{id}")
    public Answer update(@LoginUser User loginUser, @PathVariable long id, String comment) {
        return issueService.updateAnswer(loginUser, id, comment);
    }

    @DeleteMapping("{id}")
    public DeleteHistory deleted(@LoginUser User loginUser, @PathVariable long id) {
        return issueService.deletedAnswer(loginUser,id);

    }

}
