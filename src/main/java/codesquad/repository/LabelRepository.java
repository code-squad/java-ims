package codesquad.repository;

import codesquad.domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label,Long> {

}
