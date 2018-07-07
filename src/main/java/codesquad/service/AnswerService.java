package codesquad.service;

import codesquad.domain.*;
import codesquad.dto.AnswersDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AnswerService {
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    public Answer addAnswer(User loginUser, long issueId, String contents) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(EntityNotFoundException::new);
        Answer answer = new Answer(loginUser, issue, contents);
        return answerRepository.save(answer);
    }

    @Transactional
    public void deleteAnswer(User loginUser, long id) {
        Answer savedAnswer = answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        savedAnswer.checkAnswerDelete(loginUser);
        answerRepository.delete(savedAnswer);
    }

    public AnswersDto getAnswers() {
        List<Answer> answers = answerRepository.findAll();
        return new AnswersDto(answers);
    }
}
