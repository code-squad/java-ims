package codesquad.web.issue;

import codesquad.domain.issue.Comment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues/{issueId}/answers")
public class ApiCommentController {

    @PostMapping("")
    public Comment create() {
        return null;
    }
}
