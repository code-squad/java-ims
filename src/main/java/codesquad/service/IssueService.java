package codesquad.service;

import codesquad.CannotApplyException;
import codesquad.UnAuthorizedException;
import codesquad.domain.deletehistory.DeleteHistory;
import codesquad.domain.deletehistory.DeleteHistoryRepository;
import codesquad.domain.issue.Comment;
import codesquad.domain.issue.CommentRepository;
import codesquad.domain.issue.Issue;
import codesquad.domain.issue.IssueRepository;
import codesquad.domain.label.LabelRepository;
import codesquad.domain.milestone.MilestoneRepository;
import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IssueService {
    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    @Resource(name = "commentRepository")
    private CommentRepository commentRepository;

    public Issue add(User loginUser, IssueDto issueDto) {
        issueDto.setWriter(loginUser);
        return issueRepository.save(issueDto._toIssue());
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    //show용(로그인 안한 유저도 이슈를 볼 수 있다.) -->안되게 해야하나??
    public Issue findById(long id) {
        return issueRepository.findById(id)
                .orElseThrow(UnAuthorizedException::new);
    }

    //show 외
    public Issue findById(long id, User loginUser) {
        return issueRepository.findById(id)
                .filter(issue -> issue.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void update(User loginUser, long id, IssueDto updatedIssue) {
        Issue originalIssue = this.findById(id, loginUser);
        originalIssue.update(loginUser, updatedIssue._toIssue());
        issueRepository.save(originalIssue);
    }

    @Transactional
    public void delete(User loginUser, long id) {
        Issue originalIssue = this.findById(id, loginUser);
        List<DeleteHistory> histories = originalIssue.delete(loginUser);
        deleteHistoryRepository.saveAll(histories);
    }

    @Transactional
    public void setMilestone(User loginUser, long issueId, long milestoneId) {
        //loginUser가 필요한가?? 로그인 했는지 체크를 여기서 해야하는가?
        Issue currentIssue = issueRepository.findById(issueId).get();
        currentIssue.setMilestone(milestoneRepository.findById(milestoneId).get());
        issueRepository.save(currentIssue);     //@Transactional를 하면 굳이 save할 필요가 없지 않음?? 그래도 해야하는게 맞을까??
        log.debug("currentIssue!!!!!!!!!!!!!!!!!!!!!!!!! :{}", currentIssue.getMilestone());
    }

    @Transactional
    public void setAssignee(User loginUser, long issueId, long assigneeId) {
        if (loginUser.isGuestUser()) throw new CannotApplyException("you can't apply the assignee to this issue");
        Issue currentIssue = issueRepository.findById(issueId).get();
        currentIssue.setAssignee(userRepository.findById(assigneeId).get());
        issueRepository.save(currentIssue);
    }

    @Transactional
    public void setLabel(User loginUser, long issueId, long labelId) {
        if (loginUser.isGuestUser()) throw new CannotApplyException("you can't apply the label to this issue");
        Issue currentIssue = issueRepository.findById(issueId)
                .orElseThrow(UnAuthorizedException::new);
        currentIssue.setLabel(labelRepository.findById(labelId)
                .orElseThrow(UnAuthorizedException::new));
        issueRepository.save(currentIssue);
    }

    @Transactional        //얘 지정 하면 왜 자동 디비에 저장될까 --> 트랜잭션이 끝난 시점에 em의 플러쉬메소드가 실행되면서, 영속성컨텍스트에 있는 엔티티를 디비에 저장하는것임?(디비 어느 테이블인 줄 알고 저장시킴??) 안쓸 경우는 직접 레포지터리.save 해줘야함
    public Comment addComment(User loginUser, long issueId, Comment comment) {
        if(loginUser.isGuestUser()) throw new UnAuthorizedException("로그인이 필요합니다.");
        Issue currentIssue = issueRepository.findById(issueId)
                .orElseThrow(UnAuthorizedException::new);       //얘 메세지 입력은 어떻게?
        comment.setWriter(loginUser);
        currentIssue.addComment(comment);
        return comment;
    }

}
