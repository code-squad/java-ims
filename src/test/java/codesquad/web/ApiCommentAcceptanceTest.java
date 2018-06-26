package codesquad.web;

import codesquad.domain.Comment;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiCommentAcceptanceTest extends AcceptanceTest {

    private static final String ADD_COMMENT_URL = "/api/issues/1/comments";
    private static final long DEFAULT_ISSUE_ID = 1L;

    @Test
    public void add() {
        Comment newComment = new Comment("test comment1");
        ResponseEntity<Comment> response = createResource(ADD_COMMENT_URL, newComment, Comment.class, basicAuthTemplate());
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getContent(), is("test comment1"));
        String location = response.getHeaders().getLocation().getPath();

        Comment comment = getResource(location, Comment.class, template());
        assertThat(comment.getWriter().getUserId(), is(findDefaultUser().getUserId()));
        assertThat(comment.getContent(), is("test comment1"));
    }

    @Test
    public void add_NOT_Logged_In() {
        Comment newComment = new Comment("test comment2");
        ResponseEntity<Comment> response = createResource(ADD_COMMENT_URL, newComment, Comment.class, template());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void get_EntityNotFound() {
        String invalidUri = "/api/issues/1/comments/4";
        Comment comment = getResource(invalidUri, Comment.class, template());
        assertNull(comment);
    }

    @Test
    public void update() {
        Comment original = new Comment("original comment2");
        ResponseEntity<Comment> response = createResource(ADD_COMMENT_URL, original, Comment.class, basicAuthTemplate());
        String location = response.getHeaders().getLocation().getPath();

        Comment updated = new Comment("updated comment2");
        basicAuthTemplate().put(location, updated);

        Comment afterUpdate = getResource(location, Comment.class, template());
        assertThat(afterUpdate.getContent(), is("updated comment2"));
    }

    @Test
    public void update_NOT_Logged_In() {
        Comment original = new Comment("original comment3");
        ResponseEntity<Comment> response = createResource(ADD_COMMENT_URL, original, Comment.class, basicAuthTemplate());
        String location = response.getHeaders().getLocation().getPath();

        Comment updated = new Comment("updated comment3");
        template().put(location, updated);

        Comment afterUpdate = getResource(location, Comment.class, template());
        assertThat(afterUpdate.getContent(), is("original comment3"));
    }

    @Test
    public void delete() {
        Comment original = new Comment("original comment4");
        ResponseEntity<Comment> response = createResource(ADD_COMMENT_URL, original, Comment.class, basicAuthTemplate());
        String location = response.getHeaders().getLocation().getPath();

        basicAuthTemplate().delete(location);

        Comment afterDeletion = getResource(location, Comment.class, template());
        assertNull(afterDeletion);
    }

    @Test
    public void delete_NOT_Logged_In() {
        Comment original = new Comment("original comment4");
        ResponseEntity<Comment> response = createResource(ADD_COMMENT_URL, original, Comment.class, basicAuthTemplate());
        String location = response.getHeaders().getLocation().getPath();

        template().delete(location);

        Comment afterDeletion = getResource(location, Comment.class, template());
        assertNotNull(afterDeletion);
        assertThat(afterDeletion.getContent(), is("original comment4"));
    }
}