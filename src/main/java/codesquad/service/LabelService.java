package codesquad.service;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelService {
    @Resource(name = "labelRepository")
    LabelRepository labelRepository;

    public Label addLabel(Label label) {
        return labelRepository.save(label);
    }

    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    public Label getLabel(Long labelId) {
        return labelRepository.getOne(labelId);
    }
}