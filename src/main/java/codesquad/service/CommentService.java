package codesquad.service;

import codesquad.domain.Comment;
import codesquad.domain.CommentRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    public Comment create(User loginUser, Issue issue, CommentDto commentDto) {
        Comment comment = commentDto._toComment();
        comment.writeBy(loginUser);
        comment.toIssue(issue);
        return commentRepo.save(comment);
    }

    @Transactional
    public Comment update(User loginUser, Issue issue, Long id, CommentDto updateCommentDto) {
        Comment comment = commentRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return comment.update(loginUser, issue, updateCommentDto);
    }
}
