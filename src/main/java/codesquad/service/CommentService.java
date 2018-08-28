package codesquad.service;

import codesquad.domain.Comment;
import codesquad.domain.CommentRepository;
import codesquad.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentService {

    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

    public void create(User writer, Comment comment) {
        comment.writtenby(writer);
        commentRepository.save(comment);
    }

    public Iterable<Comment> findAllByIssueId(Long issueId) {
        return commentRepository.findAllByIssueId(issueId);
    }
}
