package codesquad.web.issue;

import codesquad.domain.User;
import codesquad.domain.issue.Comment;
import codesquad.domain.issue.CommentRepository;
import codesquad.service.IssueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class CommentService {

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

    @Transactional
    public Comment create(User loginUser, long issueId, Comment comment) {
        comment.setWriter(loginUser);
        issueService.findById(issueId).addComment(comment);
        return comment;
    }
}
