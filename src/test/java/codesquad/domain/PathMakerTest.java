package codesquad.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PathMakerTest {
    private static final Logger log = LoggerFactory.getLogger(PathMakerTest.class);

    @Autowired
    private PathMaker pathMaker;

    @Test
    public void makeRandomDirPath() {
        assertThat(pathMaker.makeRandomDirPath().startsWith("\\"), is(true));
    }

    @Test
    public void getFullPath() {
        assertThat(pathMaker.getFullPath("sample.txt", "\\12345678")
                .equals("target\\files\\12345678\\sample.txt"), is(true));
    }

    @Test
    public void getRootLocation() {
        assertThat(pathMaker.getRootLocation(), is("target\\files"));
    }
}
