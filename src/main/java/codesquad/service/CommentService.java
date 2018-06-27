package codesquad.service;

import codesquad.domain.*;
import codesquad.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepo;

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

    @Transactional
    public void delete(User loginUser, Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        deleteHistoryRepo.save(comment.delete(loginUser));
    }
}
