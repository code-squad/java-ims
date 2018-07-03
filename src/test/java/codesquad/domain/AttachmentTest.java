package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.PathResource;

import java.io.File;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AttachmentTest {

    private Attachment attachment;
    private String fileName;

    @Before
    public void setup() {
        fileName = "test.txt";
        attachment = new Attachment(fileName, 2L, UserTest.JAVAJIGI);
    }

    @Test
    public void parseTest() {
        String name = attachment.parseExtension(fileName);
        assertThat(name, is(".txt"));
    }

    @Test
    public void parseTest2() {
        fileName = "riverway.hwp";
        String name = attachment.parseExtension(fileName);
        assertThat(name, is(".hwp"));
    }

    @Test
    public void randomNameTest() {
        String name = attachment.randomName(fileName);
        assertThat(name.length(), is(32+4));
    }

    @Test
    public void randomNameTest2() {
        fileName = "spring.docx";
        String name = attachment.randomName(fileName);
        assertThat(name.length(), is(32+5));
    }
}
