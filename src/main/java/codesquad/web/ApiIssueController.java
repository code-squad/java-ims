package codesquad.web;

import codesquad.domain.user.User;
import support.domain.Result;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issue")
public class ApiIssueController {
    private static final Logger log = getLogger(ApiIssueController.class);

    @Autowired
    private IssueService issueService;

    @DeleteMapping("/{id}")
    public Result delete(@LoginUser User loginUser, @PathVariable long id) {
        try{
            issueService.delete(loginUser, id);
            log.debug("삭제됨");
            return Result.ok();
        } catch (Exception e) {
            return Result.error("이슈 작성자와 로그인 아이디가 다릅니다.");
        }

    }
}
