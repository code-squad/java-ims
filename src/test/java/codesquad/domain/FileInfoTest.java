package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileInfoTest {
    private static final Logger log = LoggerFactory.getLogger(FileInfoTest.class);

    @Autowired
    private PathMaker pathMaker;

    private MultipartFile file;

    @Before
    public void setUp() {
        file = new MockMultipartFile("readme", "readme.txt"
                , MediaType.TEXT_PLAIN_VALUE, "plz readme".getBytes());
    }

    @Test
    public void name() {
        FileInfo fileInfo = new FileInfo("readme.txt", pathMaker.makeRandomDirPath());
        assertThat(fileInfo.getName().equals("readme.txt"), is(true));
    }

    @Test
    public void getDirPath() {
        FileInfo fileInfo = new FileInfo(file, pathMaker.makeRandomDirPath(), 1L);

        assertNotNull(fileInfo.getDirPath());
        assertThat(fileInfo.getDirPath().startsWith("\\"), is(true));
    }

    @Test
    public void getFullPath() {
        FileInfo fileInfo = new FileInfo(file, pathMaker.makeRandomDirPath(), 1L);

        assertThat(fileInfo.getFullPath(pathMaker).startsWith("target\\files\\"), is(true));
        assertThat(fileInfo.getFullPath(pathMaker).contains("readme.txt"), is(true));
    }

    @Test
    public void addNumberToFilename() {
        FileInfo fileInfo = new FileInfo("readme.txt", pathMaker.makeRandomDirPath());
        fileInfo.addNumberToFilename();

        assertThat(fileInfo.getName().equals("readme0.txt"), is(true));
    }
}
