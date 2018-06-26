package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CommentTest {

    private Comment original;
    private static final User WRITER = new User("writer", "password", "name");
    private static final User NOT_WRITER = new User("notWriter", "password", "name");

    @Before
    public void setUp() throws Exception {
        original = new Comment("original content");
        original.setWriter(WRITER);
    }

    @Test
    public void update() {
        Comment updated = new Comment("updated content");
        original.update(WRITER, updated);
        assertEquals(updated.getContent(), original.getContent());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_User_Mismatch() {
        Comment updated = new Comment("updated content1");
        original.update(NOT_WRITER, updated);
        assertNotEquals(updated.getContent(), original.getContent());
    }

    @Test
    public void delete() {
        original.delete(WRITER);
        assertTrue(original.isDeleted());
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_User_Mismatch() {
        original.delete(NOT_WRITER);
        assertFalse(original.isDeleted());
    }
}