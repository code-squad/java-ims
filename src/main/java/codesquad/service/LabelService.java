package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.User;
import codesquad.dto.LabelDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    private static final Logger logger = getLogger(LabelService.class);

    @Autowired
    private MessageSource messageSource;

    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    public void createLabel(User loginUser, LabelDto labelDto) {
        labelRepository.save(labelDto._toLabel(loginUser));
    }

    @Transactional
    public void registerLabel(User loginUser, Issue issue, Long labelId) throws UnAuthenticationException {
        if(!issue.isOneSelf(loginUser)) {
            throw new UnAuthenticationException(
                    messageSource.getMessage("error.not.supported", null, Locale.getDefault()));
        }
        issue.setLabel(labelRepository.findById(labelId).orElse(null));
    }
}
