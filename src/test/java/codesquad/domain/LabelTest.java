package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.User.GUEST_USER;
import static codesquad.domain.UserTest.JAVAJIGI;

public class LabelTest extends BaseTest {
    public static final Label originalLabel = new Label(1L,"Bug","버그 관련 이슈");
    public static final Label updatedLabel = new Label("updatedBug","버그 관련 이슈 업데이트");

    @Test
    public void update_success() {
        originalLabel.update(JAVAJIGI, updatedLabel);
        softly.assertThat(originalLabel.getName()).isEqualTo(updatedLabel.getName());
        softly.assertThat(originalLabel.getExplanation()).isEqualTo(updatedLabel.getExplanation());
    }

    @Test(expected = CannotUpdateException.class)
    public void update_fail() {
        originalLabel.update(GUEST_USER,updatedLabel);
    }

    @Test
    public void delete_success() {
        originalLabel.delete(JAVAJIGI);
        softly.assertThat(originalLabel.isDeleted()).isTrue();
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_fail() {
        originalLabel.delete(GUEST_USER);
    }
}
