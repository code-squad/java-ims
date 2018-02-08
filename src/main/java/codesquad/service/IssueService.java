package codesquad.service;

import codesquad.domain.*;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name="milestoneService")
    private MilestoneService milestoneService;

    @Resource(name="answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "attachmentRepository")
    private AttachmentRepository attachmentRepository;

    public Issue add(IssueDto issueDto, User LoginUser) {
        return issueRepository.save(issueDto.toIssue(LoginUser));
    }

    @Transactional
    public void update(IssueDto issueDto, User loginUser, long issueId) {
        Issue originalIssue = findById(issueId).get();
        originalIssue.update(loginUser, issueDto);
    }

    @Transactional
    public void delete(User loginUser, long issueId) {
        Issue originalIssue = findById(issueId).get();
        originalIssue.delete(loginUser);
    }

    public Answer addAnswer(User loginUser, long id, Answer answer){
        answer.writeBy(loginUser);
        Issue issue = findById(id).get();
        issue.addAnswer(answer);

        return answerRepository.save(answer);
    }

    public Attachment addAttachment(long issueId, User loginUser, String fileName, String path){
        Attachment attachment = new Attachment(loginUser, fileName, path);
        Issue issue = findById(issueId).get();
        issue.addAttachment(attachment);
        return attachmentRepository.save(new Attachment(loginUser, fileName, path));
    }

    public List<Attachment> findAllAttachments(){
        return attachmentRepository.findAll();
    }

    public Optional<Attachment> findByAttachmentId(long id) {return attachmentRepository.findById(id);}

    public Answer findAnswerById(long id){
        return answerRepository.findAnswerById(id);
    }


    public Iterable<Issue> findAll() {
        return issueRepository.findAllByDeleted(false);
    }

    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }

}
