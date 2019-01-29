package codesquad.domain;

import codesquad.exception.UnAuthorizedException;
import org.junit.Test;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.RED;
import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest  {

    @Test
    public void update() {
        String updateAnswer = "updateAnswer";
        User writer = RED;

        Answer answer = new Answer("answer");
        answer.writerBy(RED);

        answer.update(RED, updateAnswer);
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_different_writer() {
        String updateAnswer = "updateAnswer";
        User writer = RED;

        Answer answer = new Answer("answer");
        answer.writerBy(RED);

        answer.update(JAVAJIGI, updateAnswer);
    }

    @Test
    public void delete() {
        Answer answer = new Answer("answer");
        answer.writerBy(RED);

        answer.delete(RED);
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_different_writer() {
        Answer answer = new Answer("answer");
        answer.writerBy(RED);

        answer.delete(JAVAJIGI);
        assertThat(answer.isDeleted()).isTrue();
    }
}
