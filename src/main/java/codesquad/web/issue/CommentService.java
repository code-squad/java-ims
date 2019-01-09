package codesquad.web.issue;

import codesquad.UnAuthorizedException;
import codesquad.domain.history.DeleteHistoryRepository;
import codesquad.domain.user.User;
import codesquad.domain.issue.Comment;
import codesquad.domain.issue.CommentRepository;
import codesquad.service.IssueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class CommentService {

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    @Transactional
    public Comment create(User loginUser, long issueId, Comment comment) {
        comment.setWriter(loginUser);
        issueService.findById(issueId).addComment(comment);
        return comment;
    }

    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Comment findById(User loginUser, long commentId) {
        Comment comment = findById(commentId);
        if(!comment.isOwner(loginUser)) throw new UnAuthorizedException();
        return comment;
    }

    @Transactional
    public Comment delete(User loginUser, long issueId, long commentId) {
        Comment comment = findById(commentId);
        deleteHistoryRepository.save(comment.delete(loginUser, issueService.findById(issueId)));
        return comment;
    }

    @Transactional
    public Comment update(User loginUser, long issueId, long commentId, Comment updateComment) {
        Comment comment = findById(commentId);
        return comment.update(loginUser, issueService.findById(issueId), updateComment);
    }
}
