package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        if (loginUser == null || !issueService.delete(loginUser, id)) {
            throw new UnAuthorizedException();
        }

        return "/issues";
    }
}
