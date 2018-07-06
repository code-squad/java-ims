package codesquad.web;

import codesquad.domain.Comment;
import codesquad.domain.User;
import codesquad.dto.CommentDto;
import codesquad.security.LoginUser;
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
@RequestMapping("/api/issues/{id}/comments")
public class ApiCommentController {

    private static final Logger log = LoggerFactory.getLogger(ApiCommentController.class);
    @Resource(name = "issueService")
    private IssueService issueService;

    @PostMapping("")
    public ResponseEntity<Comment> addComment(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody CommentDto commentDto) {
        log.debug("Comment : {}", commentDto.getComment());
        Comment comment = issueService.addComment(loginUser, id, commentDto.toComment());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/issues/%d/comments/%d", id, comment.getId())));
        return new ResponseEntity<Comment>(comment, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public Comment showComment(@PathVariable long id, @PathVariable long commentId) {
        return issueService.findComment(commentId);
    }

    @PutMapping("/{commentId}")
    public Comment update(@LoginUser User loginUser, @PathVariable long commentId, @Valid @RequestBody CommentDto target) {
        return issueService.updateComment(loginUser, commentId, target.toComment());
    }

    @DeleteMapping("/{commentId}")
    public void delete(@LoginUser User loginUser, @PathVariable long commentId) {
        issueService.deleteComment(loginUser, commentId);
    }
}
