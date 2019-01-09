package codesquad.service;

import codesquad.domain.label.Label;
import codesquad.domain.label.LabelRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LabelService {
    private static final Logger log = getLogger(LabelService.class);

    @Autowired
    LabelRepository labelRepository;

    public List<Label> findAll() {
        return labelRepository.findAll();
    }
}
