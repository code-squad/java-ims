package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.RED;

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
}
