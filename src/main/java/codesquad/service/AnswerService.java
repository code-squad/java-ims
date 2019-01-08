package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Value("$error.not.oneself")
    private String notOneSelfErrorMessage;

    @Transactional
    public Answer createAnswer(User loginUser, Issue issue, AnswerDto answerDto) {
        Answer answer = answerDto._toAnswer();
        issue.addAnswer(answer);
        return answer.applyWriter(loginUser);
    }

    @Transactional
    public void updateAnswer(User loginUser, Issue issue, AnswerDto updatedAnswer, Long answerId) throws UnAuthenticationException {
        Answer originAnswer = answerRepository.findById(answerId).orElse(null);
        if(!originAnswer.isOneSelf(loginUser)) {
            throw new UnAuthenticationException(notOneSelfErrorMessage);
        }
        issue.updateAnswer(originAnswer, updatedAnswer._toAnswer());
    }

    @Transactional
    public void deleteAnswer(User loginUser, Long answerId) throws UnAuthenticationException {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        if(!answer.isOneSelf(loginUser)) {
            throw new UnAuthenticationException(notOneSelfErrorMessage);
        }
        /* 확인 필요) issue가 answer를 가지고 있긴 하지만, answer 참조를 가지고 있는것이기 때문에 answer만 바꾸면 되지 않을까?! */
        answer.setDeleted(true);
    }

    public AnswerDto detailAnswer(User loginUser, Long answerId) throws UnAuthenticationException {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        if(!answer.isOneSelf(loginUser)) {
            throw new UnAuthenticationException(notOneSelfErrorMessage);
        }
        return answer._toAnswerDto();
    }
}
