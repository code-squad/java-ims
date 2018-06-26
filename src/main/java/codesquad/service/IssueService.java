package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.domain.*;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class IssueService {
    private static final Logger log =  LoggerFactory.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    public Issue add(User loginUser, IssueDto issueDto) {
        log.info("issueDto : {}", issueDto.toString());
        return issueRepository.save(issueDto.toIssue().writeBy(loginUser));
    }

    public Issue findById(long id) {
        log.info("findById method called : {}", issueRepository.findById(id).get().toString());
        return issueRepository.findById(id).get();
    }

    @Transactional
    public void update(User loginUser, long id, IssueDto updateIssue) throws CannotDeleteException {
        log.info("update method called");
        Issue original = issueRepository.findById(id).get();
        original.update(loginUser, updateIssue.toIssue());
    }

    @Transactional
    public void  delete(User loginUser, long id) throws CannotDeleteException {
        Issue issue = issueRepository.findById(id).get();
        if (!issue.isOwner(loginUser))
            throw new CannotDeleteException("자신이 쓴 글만 삭제할 수 있습니다.");
        log.info("delete success");
        issueRepository.deleteById(id);
    }

    @Transactional
    public void setMileStone(User loginUser, long issueId, MileStone mileStone) throws CannotDeleteException {
        Issue issue = issueRepository.findById(issueId).get();
        if (!issue.isOwner(loginUser))
            throw new CannotDeleteException("자신이 쓴 글만 설정할 수 있습니다.");
        issue.updateMileStone(loginUser, mileStone);
    }

    @Transactional
    public void setAssginee(User loginUser, long issueId, long id) throws CannotDeleteException {
        Issue issue = issueRepository.findById(issueId).get();
        if (!issue.isOwner(loginUser))
            throw new CannotDeleteException("자신이 쓴 글만 담당자를 설정할 수 있습니다.");
        log.info("setAssginee called");
        issue.updateAssignee(loginUser, userRepository.findById(id).get());
    }

    @Transactional
    public void setLabel(User loginUser, long issueId, long id) throws CannotDeleteException {
        Issue issue = issueRepository.findById(issueId).get();
        if (!issue.isOwner(loginUser))
            throw new CannotDeleteException("자신이 쓴 글만 라벨을 설정할 수 있습니다.");
        issue.updateLabel(loginUser, Label.values()[(int)id]);
    }

    public Answer addAnswer(long issueId, User answerWriter, String contents) {
        Issue issue = issueRepository.findById(issueId).get();
        return answerRepository.save(new Answer(answerWriter, issue, contents));
    }

    public List<Answer> list() {
        log.info("list method called");
        return answerRepository.findAll();
    }

    @Transactional
    public void deleteAnswer(long issueId, User loginUser, long answerId) throws CannotDeleteException {
        Answer answer = answerRepository.findById(answerId).get();
        if (!answer.isSameWriter(loginUser))
            throw new CannotDeleteException("자신이 쓴 댓글만 삭제할 수 있습니다.");
        deleteHistoryRepository.save(answer.delete(loginUser));
    }
}
