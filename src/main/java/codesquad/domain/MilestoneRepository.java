package codesquad.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MilestoneRepository extends CrudRepository<Milestone, Long> {
    @Override
    Optional<Milestone> findById(Long id);
}
