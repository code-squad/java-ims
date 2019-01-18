package codesquad.service;

import codesquad.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@Service
public class LabelService {
    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    public void add(Label label) {
        labelRepository.save(label);
    }

    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    public Label findById(long id) {
        return labelRepository.findById(id).orElseThrow(UnknownError::new);
    }

    @Transactional
    public Issue setLabel(long issueId, long id) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(UnknownError::new);
        issue.setLabel(labelRepository.findById(id).orElseThrow(UnknownError::new));
        return issue;
    }

    public Label create(User user, Label label) {
        label.writeBy(user);
        return labelRepository.save(label);
    }
}
