package codesquad.web;

import codesquad.domain.Comment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
public class ApiCommentController {
    private static final Logger log = LoggerFactory.getLogger(ApiCommentController.class);

    @Resource(name = "commentService")
    private CommentService commentService;

    @GetMapping("{commentId}")
    public ResponseEntity<Comment> getComment(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long commentId) {
        Comment comment = commentService.getOne(issueId, commentId);
        return ResponseEntity.status(HttpStatus.OK).location(URI.create(comment.generatedUri(issueId))).body(comment);
    }

    @PostMapping()
    public ResponseEntity<Comment> create(@LoginUser User writer, @PathVariable Long issueId, @RequestBody Comment comment) {
        log.debug("comment : {}", comment.toString());
        Comment createdComment = commentService.create(writer, comment, issueId);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(createdComment.generatedUri(issueId))).body(createdComment);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<Comment> update(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long commentId, @RequestBody Comment updateComment) {
        updateComment.writtenby(user);
        log.debug("updatedComment : {}", updateComment.toString());
        Comment savedComment = commentService.update(issueId, commentId, updateComment);
        log.debug("savedComment : {}", savedComment.toString());

        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<>();
        headers.add("location", savedComment.generatedUri(issueId));
        return ResponseEntity.status(HttpStatus.OK).location(URI.create(savedComment.generatedUri(issueId))).body(savedComment);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> delete(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long commentId) {
        log.debug("api comment delete is called");
        commentService.delete(user, issueId, commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
