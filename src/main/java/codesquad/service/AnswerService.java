package codesquad.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.User;

@Service
public class AnswerService {
	
	@Autowired
	AnswerRepository answerRepository;
	
	public Answer findById(long id) {
		return answerRepository.findOne(id);
	}

	public Answer create(Answer answer, User loginUser) {
		answer.setWriter(loginUser);
		return answerRepository.save(answer);
	}

	@Transactional
	public void update(long id, String contents, User loginUser) {
		Answer oldAnswer = findById(id);
		oldAnswer.update(contents, loginUser);
	}

	@Transactional
	public Answer delete(long id, User loginUser) {
		Answer oldAnswer = findById(id);
		oldAnswer.delete(loginUser);
		return oldAnswer;
	}
	
	

}
