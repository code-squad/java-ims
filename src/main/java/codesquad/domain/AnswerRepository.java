package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    Iterable<Answer> findByDeleted(boolean b);

    List<Answer> findByIssueId(long id);
}
