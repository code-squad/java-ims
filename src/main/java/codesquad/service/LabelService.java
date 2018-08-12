package codesquad.service;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LabelService {

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    public void save(Label label) {
        labelRepository.save(label);
    }
}
