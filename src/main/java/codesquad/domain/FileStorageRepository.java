package codesquad.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStorageRepository extends JpaRepository<FileInfo, Long> {

}
