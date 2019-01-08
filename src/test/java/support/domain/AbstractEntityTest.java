package support.domain;

import codesquad.domain.Avatar;
import codesquad.domain.User;
import org.junit.Test;
import support.test.BaseTest;

public class AbstractEntityTest extends BaseTest {

    @Test
    public void equals() {
        User user1 = new User(1L, "aaaaa", "aaaaa", "aaaaa", Avatar.DEFAULT_AVATAR);
        User user2 = new User(1L, "bbbbb", "bbbbb", "bbbbb", Avatar.DEFAULT_AVATAR);
        softly.assertThat(user1.equals(user2)).isTrue();
    }
}