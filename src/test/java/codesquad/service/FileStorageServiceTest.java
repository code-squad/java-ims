package codesquad.service;

import codesquad.domain.DirectoryPathMaker;
import codesquad.domain.FileInfo;
import codesquad.domain.FileStorageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FileStorageServiceTest {

    @Mock
    private FileStorageRepository fileStorageRepository;

    @InjectMocks
    private FileStorageService fileStorageService;

    private String originalFilename = "foo.txt";
    private MultipartFile multipartFile = new MockMultipartFile("foo", originalFilename,
            MediaType.TEXT_PLAIN_VALUE, "Hard Learner".getBytes());
    private FileInfo fileInfo = new FileInfo(multipartFile, new DirectoryPathMaker().makePath(), 1L);

    @Before
    public void setUp() {
        Mockito.when(fileStorageRepository.getOne(fileInfo.getId())).thenReturn(fileInfo);
    }


    @Test
    public void serviceAutowired() {
        assertNotNull(fileStorageService);
    }

    @Test
    public void storeFile() {
        FileInfo fileInfo = fileStorageService.store(multipartFile, 1L);

        assertNotNull(fileStorageService.getOne(fileInfo.getId()));
        assertThat(fileInfo.getPath().startsWith("target/files/"),is(true));
        assertThat(fileInfo.getPath().toString().contains(originalFilename), is(true));
    }
}
