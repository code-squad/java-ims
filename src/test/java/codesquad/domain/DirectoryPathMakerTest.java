package codesquad.domain;

import org.junit.Test;

import java.nio.file.Path;

public class DirectoryPathMakerTest {
    @Test
    public void makePath() {
        DirectoryPathMaker pathMaker = new DirectoryPathMaker();
        Path dirPath = pathMaker.makePath();
    }
}
