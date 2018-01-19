package codesquad.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MileStoneRepository extends JpaRepository<MileStone, Long> {

	Optional<MileStone> findBySubject(String subject);

}
