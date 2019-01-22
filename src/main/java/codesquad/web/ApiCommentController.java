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
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
public class ApiCommentController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "commentService")
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<Comment> create(@LoginUser User loginUser, @PathVariable long issueId, @Valid String comment) {
        Comment newComment = commentService.create(loginUser, issueId, comment);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/issues/%d/comments", issueId)));
        return new ResponseEntity<>(newComment, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Comment updateForm(@PathVariable long id) {
        return commentService.findById(id);
    }

    @PutMapping("/{id}")
    public Comment update(@LoginUser User loginUser, @PathVariable long id, @Valid String comment) {
        return commentService.update(loginUser, id, comment);
    }

    @DeleteMapping("/{id}")
    public void delete(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long id) {
        commentService.delete(loginUser, id);
    }
}
