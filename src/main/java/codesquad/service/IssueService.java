package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

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
        originalIssue.update(loginUser,updatedIssue._toIssue());
        issueRepository.save(originalIssue);
    }
}
