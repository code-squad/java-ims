package codesquad.service;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
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

    @Transactional
    public Label add(Label label) throws Exception {
        if (labelRepository.findByName(label.getName()).isPresent()) throw new Exception();
        return labelRepository.save(label);
    }

    @Transactional
    public void update(long id, Label updatedLabel) {
        Label label = findById(id);
        label.update(updatedLabel);
    }

    @Transactional
    public void delete(long id) {
        labelRepository.deleteById(id);
    }

    @Transactional
    public void setIssue(long issueId, long labelId) {
        findById(labelId).setIssues(issueService.findById(issueId));
    }
}
