package codesquad.repository;

import codesquad.domain.FileInfo;
import codesquad.domain.FileStorageRepository;
import codesquad.domain.PathMaker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileStorageRepositoryTest {

    @Autowired
    private FileStorageRepository fileStorageRepository;

    @Autowired
    private PathMaker pathMaker;

    @Test
    public void save() {
        Long issueId = 1L;
        MultipartFile multipartFile = new MockMultipartFile("foo", "foo.txt",
                MediaType.TEXT_PLAIN_VALUE, "Hard Learner".getBytes());
        FileInfo fileInfo = new FileInfo(multipartFile, pathMaker.makeRandomDirPath(), issueId);

        fileInfo = fileStorageRepository.save(fileInfo);
        assertThat(fileInfo.getName().equals("foo.txt"), is(true));
        assertThat(fileInfo.getDirPathWithRoot(pathMaker).startsWith("target\\files\\"), is(true));
    }
}
