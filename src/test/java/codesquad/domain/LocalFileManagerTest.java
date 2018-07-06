package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.WritableResource;
import org.springframework.mock.web.MockMultipartFile;
import support.converter.AttachmentNameConverter;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class LocalFileManagerTest {
    private static final Logger log = LoggerFactory.getLogger(LocalFileManagerTest.class);

    private String path;
    private FileManager fileManager;
    private MockMultipartFile mockFile;

    @Before
    public void setUp() throws Exception {
        path = "/Users/imjinbro/Desktop/codesquad/workspace/jwp/java-ims/src/test/java/support/upload/";
        fileManager = new LocalFileManager(path);
        mockFile = new MockMultipartFile("test", "test.txt", "text/plain", "test".getBytes());
    }

    @Test
    public void upload() throws IOException {
        String savedFileName = fileManager.upload(mockFile, AttachmentNameConverter.convertName("test"));
        log.debug("save name : {}", savedFileName);
    }

    @Test
    public void download() throws IOException {
        String savedFileName = fileManager.upload(mockFile, AttachmentNameConverter.convertName("test"));
        WritableResource file = fileManager.download(savedFileName);
        assertNotNull(file);
    }
}