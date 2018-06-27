package codesquad.web;

import codesquad.domain.Comment;
import codesquad.domain.User;
import codesquad.dto.CommentDto;
import codesquad.security.LoginUser;
import codesquad.service.CommentService;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import support.web.RestResponseEntityMaker;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
public class ApiCommentController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> create(@LoginUser User loginUser, @PathVariable Long issueId, @Valid @RequestBody CommentDto commentDto) {
        Comment comment = commentService.create(loginUser, issueService.findById(issueId), commentDto);
        return RestResponseEntityMaker.of(comment, HttpStatus.CREATED);
    }
}
