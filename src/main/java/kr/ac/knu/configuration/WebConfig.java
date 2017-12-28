package kr.ac.knu.configuration;

import kr.ac.knu.annotation.Interceptive;
import kr.ac.knu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by rokim on 2017. 5. 27..
 */
@Configuration
//@EnableWebMvc
@Slf4j
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired private UserOperator userOperator;
    @Autowired private UserService userService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")
                .allowedHeaders("header1", "header2", "header3", "token", "Content-Type")
                .exposedHeaders("header1", "header2", "token", "Content-Type");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sampleInterceptor()).excludePathPatterns("/swagger-ui.html*", "/**/webjars/**", "/console/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(sampleArgumentResolver());
        argumentResolvers.add(pageableArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/resources/");
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public HandlerMethodArgumentResolver pageableArgumentResolver() {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        return resolver;
    }

    @Bean
    public HandlerMethodArgumentResolver sampleArgumentResolver() {
        HandlerMethodArgumentResolver resolver = new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter methodParameter) {
                return methodParameter.hasParameterAnnotation(ApiIgnore.class)
                        && methodParameter.getParameterType().equals(String.class)
                        && methodParameter.getParameterName().equals("springArgument");
            }

            @Override
            public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//                String accessToken = nativeWebRequest.getHeader("token");
//
//                return userOperator.getKnuUserFromAccessToken(accessToken);
                return "SPRING_ARGUMENT";
            }
        };

        return resolver;
    }

    @Bean
    public HandlerInterceptor sampleInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj) throws Exception {
                if (obj instanceof HandlerMethod == false) {
                    return true;
                }

                if (((HandlerMethod)obj).hasMethodAnnotation(Interceptive.class)) {
                    log.info("[{}]method is intercepted", ((HandlerMethod)obj).getMethod().getName());
                    httpServletRequest.setAttribute("sample", "SAMPLE");
                }

                return true;
            }
        };
    }
}