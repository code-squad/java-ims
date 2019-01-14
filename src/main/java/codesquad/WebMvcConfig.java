package codesquad;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentDummy;
import codesquad.security.BasicAuthInterceptor;
import codesquad.security.LoginUserHandlerMethodArgumentResolver;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import support.converter.LocalDateConverter;
import support.converter.LocalDateTimeConverter;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = getLogger(WebMvcConfig.class);

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationConfigurationProp applicationConfigurationProp;

   @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
        registry.addConverter(new LocalDateTimeConverter());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(30);
        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver());
    }

    @Bean
    public Attachment dummyAvatarEnvironment() {
        String fileName = environment.getProperty("file.avatar.dummy.name");
        String path = environment.getProperty("file.avatar.dummy.path");
        return new AttachmentDummy(fileName, fileName, path);
    }

    @Bean
    public Attachment dummyAvatar() {
        String fileName = applicationConfigurationProp.getDummy().getName();
        String path = applicationConfigurationProp.getDummy().getPath();
        return new AttachmentDummy(fileName, fileName, path);
    }
}
