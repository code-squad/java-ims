package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class LabelService {
    @Resource(name="labelRepository")
    private LabelRepository labelRepository;

    @Resource(name="issueService")
    private IssueService issueService;

    public Iterable<Label> findAll() {
        return labelRepository.findAll();
    }

    public Label findById(long id) {
        return labelRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Label findById(User loginUser, long id) {
        return labelRepository.findById(id)
                .filter(user -> user.isOwner(loginUser))
                .orElseThrow(() -> new UnAuthorizedException("You're Unauthorized User"));
    }

    @Transactional
    public Label add(Label label) throws Exception {
        if (labelRepository.findByName(label.getName()).isPresent()) throw new Exception();
        return labelRepository.save(label);
    }

    @Transactional
    public void update(User loginUser, long id, Label updatedLabel) throws Exception{
        Label label = findById(loginUser, id);
        label.update(updatedLabel);
    }

    @Transactional
    public void delete(User loginUser, long id) throws Exception{
        Label label = findById(loginUser, id);
        if (label.delete()) labelRepository.deleteById(id);
    }

    @Transactional
    public void setIssue(long issueId, long labelId) {
        findById(labelId).setIssues(issueService.findById(issueId));
    }
}
