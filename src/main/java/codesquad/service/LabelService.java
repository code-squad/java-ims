package codesquad.service;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelService {
    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    public void add(Label label) {
        labelRepository.save(label);
    }

    public List<Label> findAll() {
        return labelRepository.findAll();
    }
}
