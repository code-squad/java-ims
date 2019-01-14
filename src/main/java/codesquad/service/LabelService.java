package codesquad.service;

import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.repository.LabelRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service("labelService")
public class LabelService {
    private static final Logger log = getLogger(LabelService.class);

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    public Label create(User loginUser, Label label) {
       return labelRepository.save(new Label(loginUser, label));
    }

    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    public Label findByLabelId(long id) {
        return labelRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
