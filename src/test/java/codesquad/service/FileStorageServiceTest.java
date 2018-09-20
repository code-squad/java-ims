package codesquad.service;

import codesquad.domain.FileInfo;
import codesquad.domain.FileStorageRepository;
import codesquad.domain.PathMaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileStorageServiceTest {

    private static final Logger log = LoggerFactory.getLogger(FileStorageServiceTest.class);

    @Mock
    private FileStorageRepository fileStorageRepository;

    @Mock
    private PathMaker pathMaker;

    @InjectMocks
    private FileStorageService fileStorageService;

    private MultipartFile file = new MockMultipartFile("foo", "foo.txt",
            MediaType.TEXT_PLAIN_VALUE, "Hard Learner".getBytes());

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(pathMaker, "rootLocation", "target\\files");
        when(pathMaker.getFullPath("foo.txt", "\\123456789")).thenReturn("target\\files\\123456789\\foo.txt");
        when(pathMaker.getRootLocation()).thenReturn("target\\files");
        when(pathMaker.makeRandomDirPath()).thenReturn("\\123456789");
    }

    @Test
    public void saveFileInfo() {
        FileInfo fileInfo = fileStorageService.saveFileInfo(file, 1L);
        assertThat(fileInfo.getName()).isEqualTo("foo.txt");
        assertThat(fileInfo.getDirPath()).isEqualTo("\\123456789");
        assertThat(fileInfo.getFullPath(pathMaker)).isEqualTo("target\\files\\123456789\\foo.txt");
    }

    @Test
    public void saveFile() {
        FileInfo fileInfo = fileStorageService.saveFileInfo(file, 1L);

        fileInfo = fileStorageService.saveFile(file, fileInfo);
        assertThat(Paths.get(fileInfo.getFullPath(pathMaker))).exists();
    }
}