package codesquad.service;

import codesquad.domain.Comment;
import codesquad.domain.CommentRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public Comment add(User loginUser, Issue issue, Comment comment) {
        Comment added = issue.addComment(loginUser, comment);
        logger.debug("ADDED ISSUE: {}", added);
        return commentRepository.save(added);
    }

    public Comment findById(long commentId) {
        return commentRepository.findById(commentId)
                .filter(comment -> !comment.isDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void update(User loginUser, long commentId, Comment updated) {
        Comment target = findById(commentId);
        target.update(loginUser, updated);
    }

    @Transactional
    public void delete(User loginUser, long commentId) {
        Comment target = findById(commentId);
        target.delete(loginUser);
    }
}
