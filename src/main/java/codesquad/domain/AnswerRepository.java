package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByIssue(Issue issue);
    Answer findAnswerById(long id);
}
