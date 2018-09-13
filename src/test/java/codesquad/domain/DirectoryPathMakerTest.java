package codesquad.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DirectoryPathMakerTest {
    private static final Logger log =  LoggerFactory.getLogger(DirectoryPathMakerTest.class);

    @Test
    public void makePath() {
        DirectoryPathMaker pathMaker = new DirectoryPathMaker();
        Path dirPath = pathMaker.makePath();
        log.debug("dirPath : {}", dirPath);
        assertThat(dirPath.toString().contains("target\\files\\"), is(true));
    }
}
