package codesquad.web;

import codesquad.domain.Comment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
public class ApiCommentController {
    private static final Logger log =  LoggerFactory.getLogger(ApiCommentController.class);

    @Resource(name = "commentService")
    private CommentService commentService;

    @GetMapping("{commentId}")
    public Comment getComment(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long commentId) {
        return commentService.getOne(issueId, commentId);
    }

    @PostMapping()
    public ResponseEntity<Void> create(@LoginUser User writer, @PathVariable Long issueId, @RequestBody Comment comment) {
        log.debug("comment : {}", comment.toString());
        String location = commentService.create(writer, comment, issueId).generatedUri(issueId);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(location)).build();
    }

    @PutMapping("{commentId}")
    public ResponseEntity<Void> update(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long commentId, @RequestBody Comment updateComment) {
        log.debug("updatedComment : {}", updateComment.toString());
        Comment savedComment = commentService.update(issueId, commentId, updateComment);
        log.debug("savedComment : {}", savedComment.toString());
        String location = savedComment.generatedUri(issueId);

        return ResponseEntity.status(HttpStatus.OK).location(URI.create(location)).build();
    }
}
