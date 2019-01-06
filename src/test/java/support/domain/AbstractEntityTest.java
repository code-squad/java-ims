package support.domain;

import codesquad.domain.User;
import org.junit.Test;
import support.test.BaseTest;

public class AbstractEntityTest extends BaseTest {

    @Test
    public void equals() {
        User user1 = new User(1L, "aaaaa", "aaaaa", "aaaaa");
        User user2 = new User(1L, "bbbbb", "bbbbb", "bbbbb");
        softly.assertThat(user1.equals(user2)).isTrue();
    }
}