package codesquad.web.api;

import codesquad.CannotUpdateException;
import codesquad.domain.issue.Comment;
import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import support.domain.ErrorMessage;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
public class ApiCommentController {

    private static final Logger log = LoggerFactory.getLogger(ApiCommentController.class);

//    @Resource(name = "issueService")
//    private IssueService issueService;      //데브툴스로 서버 재시작하면, Consider defining a bean named 'issueService' in your configuration. 에러 뜸 (최초 서버 실행에는 안뜨고 잘 작동함 왜냐)
    //데브툴스 서버 재시작시 --> 답변 추가 버튼 누르고나서, 해당 url을 찾아가지 못함 (최초 접속땐 또 되고;;)


    @Autowired
    private IssueService issueService;

    @PostMapping("")
    public ResponseEntity create(@LoginUser User loginUser, @PathVariable long issueId, @Valid @RequestBody Comment comment) {
        Comment newComment = issueService.addComment(loginUser, issueId, comment); //얘는 직접적으로 commentRepository.save를 안해주는데?? 디비에 들어가나?
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/issues/%d", issueId)));
        return new ResponseEntity<Comment>(newComment, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long id) {
        Comment currentComment = issueService.findComment(loginUser, id);
        if(!currentComment.isOwner(loginUser)) {
            return new ResponseEntity<ErrorMessage>(new ErrorMessage("다른 사용자의 comment를 삭제할 수 없습니다."), HttpStatus.UNAUTHORIZED);
        }
        issueService.deleteComment(loginUser, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity updateForm(@LoginUser User loginUser, @PathVariable long id) {
        Comment currentComment = issueService.findComment(loginUser, id);
        if(!currentComment.isOwner(loginUser)) {
            return new ResponseEntity<ErrorMessage>(new ErrorMessage("다른 사용자의 comment를 수정할 수 없습니다."), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Comment>(currentComment, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long id, @Valid @RequestBody Comment comment) {
        Comment updatedComment = issueService.updateComment(loginUser, issueId, id, comment);
        return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);
    }
}
