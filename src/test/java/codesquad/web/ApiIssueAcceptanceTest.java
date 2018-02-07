package codesquad.web;

import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void delete() throws Exception {
        long id = 2;
        basicAuthTemplate().delete(String.format("/api/issues/%d", id), String.class);

        assertNull(issueRepository.findOne(Long.valueOf(id)));
    }
}
