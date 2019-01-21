package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.issue.Comment;
import codesquad.security.LoginUser;
import codesquad.service.CommentService;
import codesquad.service.IssueService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
public class ApiCommentController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "commentService")
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<Comment> create(@LoginUser User loginUser, @PathVariable long issueId, String comment) {
        Comment newComment = commentService.create(loginUser, issueId, comment);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/issues/%d/answers/", issueId)));
        return new ResponseEntity<>(newComment, headers, HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public Answer update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody String answer) {
//        logger.debug("## update : {}", answer);
//        return answerService.update(loginUser, id, answer);
//    }
//
//    @GetMapping("/{id}/updateForm")
//    public Answer updateForm(@PathVariable long id) {
//        return answerService.findById(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@LoginUser User loginUser, @PathVariable long id) {
//        answerService.delete(loginUser, id);
//    }
}
