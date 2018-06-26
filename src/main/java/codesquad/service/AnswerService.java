package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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

    private Answer findAnswerById(User loginUser, Long answerId) throws UnAuthenticationException {
        return Optional.ofNullable(findAnswerById(answerId))
                .filter(a -> a.isWriter(loginUser))
                .orElseThrow(UnAuthenticationException::new);
    }

    @Transactional
    public Answer update(User loginUser, Long answerId, String content) throws UnAuthenticationException {
        return findAnswerById(loginUser, answerId).update(content);
    }

    @Transactional
    public void delete(User loginUser, Long answerId) throws UnAuthenticationException {
        Answer answer = findAnswerById(loginUser, answerId);
        answerRepository.delete(answer);
    }
}
