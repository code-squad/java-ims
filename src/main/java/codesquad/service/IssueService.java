package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class IssueService {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public Issue add(IssueDto issueDto) {
        return issueRepository.save(issueDto._toIssue());
    }

    @Transactional
    public Issue create(User loginUser, IssueDto issueDto) {
        issueDto.writeBy(loginUser);
        add(issueDto);
        return issueDto._toIssue();
    }

//    public Iterable<Issue> findAll() {
//        return issueRepository.findByDeleted(false);
//    }

    public List<Issue> findAll(Pageable pageable) {
        return issueRepository.findAll(pageable).getContent();
    }

    public Issue findById(long id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
