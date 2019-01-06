package codesquad.web.issue;

import codesquad.domain.DeleteHistoryRepository;
import codesquad.domain.User;
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

    public Comment delete(User loginUser, long issueId, long commentId) {
        Comment comment = findById(commentId);
        deleteHistoryRepository.save(comment.delete(loginUser, issueService.findById(issueId)));
        return comment;
    }
}
