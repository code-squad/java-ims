package codesquad.Service;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import codesquad.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void loginTest_success() throws Exception {
        User defaultUser = new User("sehwan", "test", "sehwan");
        when(userRepository.findByUserId(defaultUser.getUserId())).thenReturn(Optional.of(defaultUser));
        User user = userService.login("sehwan", "test");

        assertThat(user.getName(), is("sehwan"));
    }

    @Test(expected = UnAuthenticationException.class )
    public void loginTest_no_user() throws Exception {
        User defaultUser = new User("sehwan", "test", "sehwan");
        when(userRepository.findByUserId(defaultUser.getUserId())).thenReturn(Optional.of(defaultUser));
        when(userRepository.findByUserId("javajigi")).thenReturn(Optional.empty());
        User user = userService.login("javajigi", "test");
    }
}
