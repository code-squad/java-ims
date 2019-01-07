package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
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

    public void update(User loginUser, long id, IssueDto updatedIssue) {
        Issue originalIssue = this.findById(id, loginUser);
        originalIssue.update(loginUser, updatedIssue._toIssue());
        issueRepository.save(originalIssue);
    }

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
        log.debug("currentIssue!!!!!!!!!!!!!!!!!!!!!!!!! :{}", currentIssue.getMilestone());
    }
}
