package codesquad.repository;

import codesquad.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Iterable<Answer> findByDeleted(boolean deleted);
}
