package codesquad.service;

import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.repository.UserRepository;
import codesquad.domain.UserTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import support.test.BaseTest;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends BaseTest {
    private static final Logger log = getLogger(UserServiceTest.class);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    public User newUser() throws Exception {
        User newUser = new User("Skull", "test", "sungyul");
        return newUser;
    }

    public User updatingUser() {
        User updatingUser = new User ("Skull","test2","sungyul2");
        return updatingUser;
    }

    @Before
    public void setUp() throws Exception {
        when(userRepository.findByUserId(newUser()._toUserDto().getUserId())).thenReturn(Optional.of(newUser()));
        when(userRepository.findById(newUser().getId())).thenReturn(Optional.of(newUser()));
    }

    @Test
    public void login_success() throws Exception {
        User loginUser = userService.login(newUser()._toUserDto().getUserId(), newUser()._toUserDto().getPassword());
        softly.assertThat(loginUser).isEqualTo(newUser());
    }

    @Test(expected = UnAuthenticationException.class)
    public void login_failed_userId_mismatch() throws Exception {
        userService.login("sanjigi", newUser()._toUserDto().getPassword());
    }

    @Test
    public void update_success() throws Exception {
        User updatedUser = userService.update(newUser(), newUser().getId(), updatingUser()._toUserDto());
        softly.assertThat(updatedUser).isEqualTo(updatingUser());
    }

    @Test(expected = UnAuthenticationException.class)
    public void update_no_login() throws Exception {
        userService.update(null, newUser().getId(), updatingUser()._toUserDto());
    }

    @Test (expected = UnAuthorizedException.class)
    public void update_other_user() throws Exception {
        userService.update(UserTest.SANJIGI, newUser().getId(), updatingUser()._toUserDto());
    }
}
