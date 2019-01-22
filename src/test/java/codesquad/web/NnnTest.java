package codesquad.web;

import codesquad.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import support.test.AcceptanceTest;


//엔랙젝션
public class NnnTest extends AcceptanceTest {
    private static final Logger log = LogManager.getLogger(NnnTest.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;


    @Before
    @Transactional()
    public void setUp() {

        userRepository.deleteByUserId("test1");
        userRepository.deleteByUserId("test2");

        userRepository.save(new User("test1", "passworld", "nnn"));
        userRepository.save(new User("test2", "passworld", "nnn2"));

    }

    @Test
    @Transactional()

    public void findUser() {

        userRepository.findByUserId("test1");

    }

    @Test
    @Transactional()
    public void findIssue() {
        userRepository.findByUserId("test2");

    }
}
