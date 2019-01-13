package codesquad.service;

import codesquad.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Service
public class AnswerService {

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Transactional
    public Answer create(User loginUser, Issue issue, @Valid String newComment) {
        Answer comment = new Answer(loginUser, issue, newComment);
        issue.addComment(comment);
        return  answerRepository.save(comment);
    }

    @Transactional
    public Answer update(User loginUser, long id, @Valid String update) {
        Answer comment = answerRepository.findById(id).orElseThrow(UnknownError::new);
        comment.update(update, loginUser);
        return answerRepository.save(comment);
    }

    public Iterable<Answer> findAll() {
        return answerRepository.findByDeleted(false);
    }

    public List<Answer> findByIssueId(long id) {
        return answerRepository.findByIssueId(id);
    }

    public DeleteHistory delete(User loginUser, long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(UnknownError::new);
        answer.delete(loginUser);
        answerRepository.save(answer);
        return answer.delete(loginUser);
    }
    

}
