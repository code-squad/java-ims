package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    Optional<Milestone> findById(long id);
    List<Milestone> findAll();

}
