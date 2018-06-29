package codesquad.domain;

import org.junit.Test;

import java.io.File;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AttachmentTest {

    @Test
    public void parseTest(){
        String file = "test.txt";

        String name = file.substring(file.lastIndexOf("."));
        assertThat(name, is(".txt"));
    }
}
