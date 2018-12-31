package codesquad.service;

import codesquad.CannotDeleteException;
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

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    public Iterable<Issue> findAll() {
        return issueRepository.findByDeleted(false);
    }

    public List<Issue> findAll(Pageable pageable) {
        return issueRepository.findAll(pageable).getContent();
    }

    public Issue findById(long id) {
        //여기서 작성자일치여부 검증필요?
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Issue add(IssueDto issueDto) {
        return issueRepository.save(issueDto._toIssue());
    }

    @Transactional
    public Issue create(User loginUser, IssueDto issueDto) {
        issueDto.writeBy(loginUser);
        add(issueDto);
        return issueDto._toIssue();
    }

    @Transactional
    public Issue update(User loginUser, long id, IssueDto updatedIssueDto) {
        Issue original = findById(id);
        original.update(loginUser, updatedIssueDto._toIssue());
        return issueRepository.save(original);
    }

    @Transactional
    public Issue delete(User loginUser, long id) throws CannotDeleteException {
        Issue original = findById(id);
        deleteHistoryService.saveAll(original.delete(loginUser));
        return issueRepository.save(original);
    }
}
