package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.CannotUpdateException;
import codesquad.domain.label.Label;
import org.junit.Test;
import support.test.BaseTest;

import static codesquad.domain.user.User.GUEST_USER;
import static codesquad.domain.UserTest.JAVAJIGI;

public class LabelTest extends BaseTest {
    public static final Label ORIGINAL_LABEL = new Label(1L,"Bug","버그 관련 이슈");
    public static final Label UPDATED_LABEL = new Label("updatedBug","버그 관련 이슈 업데이트");

    @Test
    public void update_success() {
        ORIGINAL_LABEL.update(JAVAJIGI, UPDATED_LABEL);
        softly.assertThat(ORIGINAL_LABEL.getName()).isEqualTo(UPDATED_LABEL.getName());
        softly.assertThat(ORIGINAL_LABEL.getExplanation()).isEqualTo(UPDATED_LABEL.getExplanation());
    }

    @Test(expected = CannotUpdateException.class)
    public void update_fail() {
        ORIGINAL_LABEL.update(GUEST_USER, UPDATED_LABEL);
    }

    @Test
    public void delete_success() {
        ORIGINAL_LABEL.delete(JAVAJIGI);
        softly.assertThat(ORIGINAL_LABEL.isDeleted()).isTrue();
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_fail() {
        ORIGINAL_LABEL.delete(GUEST_USER);
    }
}
