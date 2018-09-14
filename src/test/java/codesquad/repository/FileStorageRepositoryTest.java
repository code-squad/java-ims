package codesquad.repository;

import codesquad.domain.DirectoryPathMaker;
import codesquad.domain.FileInfo;
import codesquad.domain.FileStorageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FileStorageRepositoryTest {
    @Autowired
    private FileStorageRepository fileStorageRepository;

    @Test
    public void save() {
        Long issueId = 1L;
        MultipartFile multipartFile = new MockMultipartFile("foo", "foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hard Learner".getBytes());
        FileInfo fileInfo = new FileInfo(multipartFile, new DirectoryPathMaker().makePath(), issueId);

        fileStorageRepository.save(fileInfo);
    }
}
