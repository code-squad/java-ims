package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.test.BaseTest;

import java.util.Optional;

import static codesquad.domain.UserTest.BRAD;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends BaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        when(userRepository.findByUserId(BRAD.getUserId())).thenReturn(Optional.of(BRAD));
    }

    @Test
    public void login_성공() throws UnAuthenticationException {
        userService.login(BRAD.getUserId(), BRAD.getPassword()).equals(BRAD);
    }

    @Test(expected = UnAuthenticationException.class)
    public void login_실패() throws UnAuthenticationException {
        userService.login(BRAD.getUserId() + "2", BRAD.getPassword()).equals(BRAD);
        userService.login(BRAD.getUserId(), BRAD.getPassword() + "2").equals(BRAD);
    }
}