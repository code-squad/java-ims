package codesquad.service;

import codesquad.domain.LabelRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LabelService {

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;
}
