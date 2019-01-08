package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class AssigneeService {

    @Value("${error.not.oneself}")
    private String oneSelfErrorMessage;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = getLogger(AssigneeService.class);

    @Transactional
    public void registerAssignee(User loginUser, Issue issue, Long assigneeId) throws UnAuthenticationException {
        if(!issue.isOneSelf(loginUser)) {
            logger.debug(oneSelfErrorMessage);
            throw new UnAuthenticationException(oneSelfErrorMessage);
        }
        issue.setAssignee(userRepository.findById(assigneeId).orElse(null));
    }
}
