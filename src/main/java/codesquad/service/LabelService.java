package codesquad.service;

import codesquad.domain.LabelRepository;
import codesquad.domain.User;
import codesquad.dto.LabelDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    private static final Logger logger = getLogger(LabelService.class);


    public List<LabelDto> findAll() {
        List<LabelDto> labelDtos = new ArrayList<>();
        labelRepository.findAll().stream().forEach(l -> labelDtos.add(l._toLabelDto()));
        return labelDtos;
    }

    public void createLabel(User loginUser, LabelDto labelDto) {
        labelRepository.save(labelDto._toLabel(loginUser));
    }
}
