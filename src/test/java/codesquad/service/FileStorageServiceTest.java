package codesquad.service;

import codesquad.domain.FileInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class FileStorageServiceTest {

    private FileStorageService fileStorageService;

    @Before
    public void setup() {
        fileStorageService = new FileStorageService();
    }

    @Test
    public void storeFile() {
        MultipartFile file = new MockMultipartFile("readme", "readme.txt", MediaType.TEXT_PLAIN_VALUE, "plz readme".getBytes());
        FileInfo fileInfo = fileStorageService.storeFile(file);
        System.out.println("fileinfo location : " + fileInfo.getPath().toString());
        assertThat(fileInfo.getName(), is("readme"));
    }
}
