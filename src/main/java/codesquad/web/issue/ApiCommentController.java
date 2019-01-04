package codesquad.web.issue;

import codesquad.domain.User;
import codesquad.domain.issue.Comment;
import codesquad.security.LoginUser;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues/{issueId}/answers")
public class ApiCommentController {
    private static final Logger log = getLogger(ApiCommentController.class);

    @Resource(name = "commentService")
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @PathVariable long issueId, @Valid @RequestBody Comment comment) {
        Comment savedComment = commentService.create(loginUser, issueId, comment);

        HttpHeaders headers = new HttpHeaders();
        URI uri = URI.create(String.format("/api/issues/%d/answers", issueId));  //todo answer아이디
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
