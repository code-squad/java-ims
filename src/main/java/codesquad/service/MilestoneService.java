package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = getLogger(MilestoneService.class);

    public Milestone saveMilestone(User loginUser, MilestoneDto milestoneDto) {
        return milestoneRepository.save(milestoneDto._toMilestone(loginUser));
    }

    public List<Milestone> findAllMilestone() {
        return milestoneRepository.findAll();
    }

    @Transactional
    public void registerMilestone(User loginUser, Issue issue, Long milestoneId) {
        if(!issue.isOneSelf(loginUser)) {
            throw new UnAuthorizedException(
                    messageSource.getMessage("error.not.oneself", null, Locale.getDefault()));
        }
    }
}
