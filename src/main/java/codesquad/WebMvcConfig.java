package codesquad;

import codesquad.security.BasicAuthInterceptor;
import codesquad.security.LoginUserHandlerMethodArgumentResolver;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import support.converter.LocalDateConverter;
import support.converter.LocalDateTimeConverter;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = getLogger(WebMvcConfig.class);

    @Override
    public void addFormatters(FormatterRegistry registry) {
        logger.debug("Call Method addFormatters");
        registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
        registry.addConverter(new LocalDateTimeConverter());
    }

    @Bean
    public MessageSource messageSource() {
        logger.debug("Call Method messageSource");
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(30);
        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        logger.debug("Call Method messageSourceAccessor");
        return new MessageSourceAccessor(messageSource);
    }

    @Bean
    public BasicAuthInterceptor basicAuthInterceptor() {
        return new BasicAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthInterceptor());
    }

    @Bean
    public LoginUserHandlerMethodArgumentResolver loginUserArgumentResolver() {
        return new LoginUserHandlerMethodArgumentResolver();
    }

    @Bean
    public String avatarDummyPath() {
        return "/Users/lee_ki_hyun/Desktop/CodeSquad/Steps/img/avatar/";
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver());
    }
}
