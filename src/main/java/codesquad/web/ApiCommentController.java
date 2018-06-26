package codesquad.web;

import codesquad.domain.Comment;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.CommentService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/issues/{id}/comments")
public class ApiCommentController {
    private static final Logger logger = LoggerFactory.getLogger(ApiCommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private IssueService issueService;

    @PostMapping("")
    public ResponseEntity<Comment> add(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody Comment comment) {
        Comment added = commentService.add(loginUser, issueService.findById(id), comment);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setLocation(URI.create(String.format("/api/issues/%d/comments/%d", id, added.getId())));
        return new ResponseEntity<>(added, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public Comment get(@PathVariable long commentId) {
        return commentService.findById(commentId);
    }

    @PutMapping("/{commentId}")
    public void update(@LoginUser User loginUser, @PathVariable long commentId, @Valid @RequestBody Comment updated) {
        commentService.update(loginUser, commentId, updated);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@LoginUser User loginUser, @PathVariable long commentId) {
        commentService.delete(loginUser, commentId);
    }
}
