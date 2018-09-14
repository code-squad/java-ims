package codesquad.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class FileInfoTest {
    private static final Logger log = LoggerFactory.getLogger(FileInfoTest.class);

    @Test
    public void name() {
        FileInfo fileInfo = new FileInfo("readme.txt");
        assertThat(fileInfo.getName().equals("readme.txt"), is(true));
    }

    @Test
    public void path() {
        // name은 확장자없는 파일 이름, original name은 확장자 붙은 파일 이름
        MultipartFile file = new MockMultipartFile("readme", "readme.txt", MediaType.TEXT_PLAIN_VALUE, "plz readme".getBytes());
        FileInfo fileInfo = new FileInfo(file.getName(), Paths.get("target/files/" + Math.abs(new Random().nextLong())));
        assertNotNull(fileInfo.getPath());
    }
}
