package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueService {
    private static final Logger log = LogManager.getLogger(IssueService.class);

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

    @Resource(name = "milestoneRepository")
    private MilestoneRepository milestoneRepository;

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "imageFileRepository")
    private ImageFileRepository imageFileRepository;

    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }

    public Iterable<Issue> findAll() {
        log.debug("findAll");
        return issueRepository.findByDeleted(false);
    }

    public void add(User loginUser, ContentsBody contentsBody) {
        issueRepository.save(Issue.ofBody(loginUser, contentsBody));
    }

    @Transactional
    public Issue update(long id, User loginUser, ContentsBody contentsBody) {
        return issueRepository.findById(id)
                .map(issue -> issue.update(loginUser, contentsBody))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void deleted(long id, User loginUser) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        deleteHistoryService.save(issue.deleted(loginUser));
    }

    @Transactional
    public void updateMilestone(Long id, Long milestoneId) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        issue.toMilestone(milestoneRepository.findById(milestoneId).get());
    }

    @Transactional
    public void updateLabel(Long id, Long labelId) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        issue.toLabel(labelRepository.findById(labelId).get());
    }

    @Transactional
    public void updateAssignee(Long id, Long userId) {
        Issue issue = issueRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        issue.toAssignee(userRepository.findById(userId).get());
    }

    @Transactional
    public Answer addAnswer(User loginUser, long issueId, String comment) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(UnAuthorizedException::new);
        Answer answer = new Answer(loginUser, comment);
        return issue.addAnswer(answer);
    }

    @Transactional
    public Answer addFileAnswer(User loginUser, long issueId, ImageFile pic) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(UnAuthorizedException::new);
        Answer answer = new Answer(loginUser, "img");
        answer.addImg(pic);
        return issue.addAnswer(answer);
    }

    public Answer findAnswerById(long id) {
        return answerRepository.findById(id).orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public Answer updateAnswer(User loginUser, long id, String comment) {
        Answer answer = answerRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        return answer.update(loginUser, comment);
    }

    @Transactional
    public DeleteHistory deletedAnswer(User loginUser, long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(UnAuthorizedException::new);
        answer.deleted(loginUser);
        return new DeleteHistory(ContentType.ANSWER, id, loginUser);
    }
}
