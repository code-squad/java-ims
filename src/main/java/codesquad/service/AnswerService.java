package codesquad.service;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

@Service("answerService")
public class AnswerService {

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(EntityNotFoundException::new);
    }
}
