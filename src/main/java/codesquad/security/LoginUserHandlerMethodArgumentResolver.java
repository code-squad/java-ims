package codesquad.security;

import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Value("${error.login}")
    private String errorMessage;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        User user = HttpSessionUtils.getUserFromSession(webRequest);

        if (!user.isGuestUser()) {
            return user;
        }
        LoginUser loginUser = parameter.getParameterAnnotation(LoginUser.class);
        if (loginUser.required()) {
            throw new UnAuthorizedException(errorMessage);
        }
        return user;
    }
}
