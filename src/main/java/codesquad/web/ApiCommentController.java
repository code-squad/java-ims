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

    /*
        왜 create의 응답 body에 comment가 담겨야 하는가?
        ajax로 comment create 요청을 했을 때, 응답으로 comment가 있어야 클라이언트에서 추가하여 바로 보여줄 수 있다.

        왜 create의 파라미터 중 Comment에 @RequestBody가 붙으면 415 error가 발생할까?
     */
    @PostMapping()
    public ResponseEntity<Comment> create(@LoginUser User writer, @PathVariable Long issueId, Comment comment) {
        log.debug("comment : {}", comment.toString());
        Comment createdComment = commentService.create(writer, comment, issueId);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(createdComment.generatedUri(issueId))).body(createdComment);
    }

    @PutMapping("{commentId}")
//    public ResponseEntity<Void> update(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long commentId, @RequestBody Comment updateComment) {
    public ResponseEntity<Comment> update(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long commentId, Comment updateComment) {
        updateComment.writtenby(user);
        log.debug("updatedComment : {}", updateComment.toString());
        Comment savedComment = commentService.update(issueId, commentId, updateComment);
        log.debug("savedComment : {}", savedComment.toString());
        String location = savedComment.generatedUri(issueId);

        return ResponseEntity.status(HttpStatus.OK).location(URI.create(savedComment.generatedUri(issueId))).body(savedComment);

    }
}
