package codesquad.service;

import org.springframework.stereotype.Service;

import codesquad.UnAuthenticationException;
import codesquad.domain.User;

@Service
public class UserService {

    public User login(String userId, String password) throws UnAuthenticationException {
        // TODO 로그인 기능 구현해야 함.
        return null;
    }

}
