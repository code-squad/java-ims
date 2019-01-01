package codesquad.service;

import codesquad.domain.label.Label;
import codesquad.domain.label.LabelBody;
import codesquad.domain.label.LabelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

@Service
public class LabelService {

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;


    public Label create(LabelBody labelBody) {
        Label label = new Label(labelBody);
        return labelRepository.save(label);
    }

    public Iterable<Label> findAll() {
        return labelRepository.findAll();
    }

    public Label findById(long id) {
        return labelRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
