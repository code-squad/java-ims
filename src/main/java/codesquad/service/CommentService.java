package codesquad.service;

import codesquad.domain.Comment;
import codesquad.domain.CommentRepository;
import codesquad.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class CommentService {

    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

    public Comment create(User writer, Comment comment, Long issueId) {
        comment.writtenby(writer);
        comment.setIssueId(issueId);
        return commentRepository.save(comment);
    }

    public Comment getOne(Long issueId, Long commentId) {
        return commentRepository.findByIssueIdAndId(issueId, commentId);
    }

    public Iterable<Comment> findAllByIssueId(Long issueId) {
        return commentRepository.findAllByIssueId(issueId);
    }

    public Comment update(Long issueId, Long commentId, Comment updateComment) {
        Comment savedComment = commentRepository.findByIssueIdAndId(issueId, commentId);
        return commentRepository.save(savedComment.update(updateComment));
    }
}
