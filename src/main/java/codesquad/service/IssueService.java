package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import codesquad.dto.CommentDto;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service("issueService")
@Transactional
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    public Issue addIssue(User writer, Issue issue) {
        issue.writeBy(writer);
        return issueRepository.save(issue);
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Issue findById(long id) {
        return issueRepository.findById(id).filter(i -> !i.isClosed()).orElseThrow(EntityNotFoundException::new);
    }

    public Issue checkOwner(long id, User loginUser){
        return issueRepository.findById(id)
                .filter(issue -> issue.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    public void update(long id, User loginUser, IssueDto target) {
        Issue origin = findById(id);
        origin.update(loginUser, target.toIssue());
    }

    public void delete(long id, User loginUser) {
        Issue issue = findById(id);
        List<DeleteHistory> deleteHistories = issue.delete(loginUser);
        deleteHistoryRepository.saveAll(deleteHistories);
    }

    public void setMilestone(long id, User loginUser, Milestone milestone){
        Issue issue = findById(id);
        issue.setMilestone(loginUser, milestone);
    }

    public void assign(User loginUser, User assignee, long id) {
        Issue issue = findById(id);
        issue.setAssignee(assignee, loginUser);
    }

    public void setLabel(User loginUser, long id, long labelId) {
        Issue issue = findById(id);
        issue.setLabel(loginUser, Label.findLabel(labelId));
    }

    public void close(User loginUser, long id) {
        Issue issue = findById(id);
        issue.setClosed(loginUser, true);
    }

    public Comment addComment(User loginUser, long id, Comment comment) {
        Issue issue = findById(id);
        comment.writeBy(loginUser);
        comment.setIssue(issue);
        return commentRepository.save(comment);
    }

    public Comment findComment(long commentId) {
        return commentRepository.findById(commentId).filter(c -> !c.isDeleted()).orElseThrow(EntityNotFoundException::new);
    }

    public Comment updateComment(User loginUser, long commentId, Comment target) {
        Comment origin = findComment(commentId);
        return origin.update(loginUser, target);
    }

    public void deleteComment(User loginUser, long commentId){
        Comment comment = findComment(commentId);
        deleteHistoryRepository.save(comment.delete(loginUser));
    }
}
