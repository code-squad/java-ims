package codesquad.service;

import codesquad.domain.AnswerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AnswerService {
    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;
}
