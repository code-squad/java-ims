package codesquad.service;

import codesquad.InvalidLoginInfoException;
import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    private User user;
    private User otherUser;

    private UserDto updateUserInfo;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        user = new User("colin", "1234", "colin");
        otherUser = new User("jinbro", "1234", "jinbro");

        updateUserInfo = new UserDto("colin", "3456", "Colin");
    }

    @Test
    public void update() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(updateUserInfo._toUser());
        User updatedUser = userService.update(user, anyLong(), updateUserInfo);
        assertEquals(updatedUser.getName(), updateUserInfo.getName());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_fail_unAuthorization() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.update(otherUser, anyLong(), updateUserInfo);
    }

    @Test
    public void login() throws Exception {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        userService.login(user.getUserId(), user.getPassword());
    }

    @Test(expected = InvalidLoginInfoException.class)
    public void login_fail_invalid_info() throws Exception {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));
        userService.login(user.getUserId(), "123124123");
    }
}