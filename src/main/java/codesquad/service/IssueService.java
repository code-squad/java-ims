package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;

    public Issue add(User loginUser, IssueDto issueDto) throws UnAuthenticationException {
        if (loginUser == null) {
            throw new UnAuthenticationException("로그인이 필요합니다.");
        }
        Issue issue = issueDto.toIssue();
        issue.writeBy(loginUser);
        return issueRepository.save(issueDto.toIssue());
    }

    public List<Issue> findAll() {
        return (List<Issue>) issueRepository.findAll();
    }

    public Issue findById(Long id) {
        return issueRepository.findOne(id);
    }

    @Transactional
    public void update(User loginUser, Long id, IssueDto issueDto) {
        Issue issue = issueRepository.findOne(id);

        if (!issue.isWritedBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 수정 또는 삭제할 수 있습니다.");
        }

        issue.update(issueDto);
    }
}
