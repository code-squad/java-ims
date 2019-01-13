package codesquad.repository;

import codesquad.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Issue,Long> {

}
