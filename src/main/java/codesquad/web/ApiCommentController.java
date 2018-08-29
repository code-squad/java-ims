package codesquad.web;

import codesquad.domain.Comment;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/issues/{issueId}/comments")
public class ApiCommentController {

    @Resource(name = "commentService")
    private CommentService commentService;

    @GetMapping("{commentId}")
    public Comment getComment(@LoginUser User user, @PathVariable Long commentId) {
        return commentService.getOne(commentId);
    }

    @PostMapping("/{issueId}/comments")
    public ResponseEntity<Void> createComment(@LoginUser User writer, @PathVariable Long issueId, Comment comment) {
        commentService.create(writer, comment);
//        return ResponseEntity.status(HttpStatus.CREATED).location()
        return null;
    }
}
