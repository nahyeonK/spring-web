package kr.ac.knu.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by rokim on 2017. 5. 27..
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserOperator userOperator;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/health", "/db/**", "/example/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/console/**").permitAll()
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable()
                .and().addFilterBefore(customAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    protected AbstractAuthenticationProcessingFilter customAuthFilter() throws Exception {
        FacebookAuthenticationFilter filter = new FacebookAuthenticationFilter(new RegexRequestMatcher("^/authenticate/login", "POST"));
        filter.setUserOperator(userOperator);
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
            httpServletRequest.getRequestDispatcher("/authenticate/logon").forward(httpServletRequest, httpServletResponse);
        });
        filter.setAuthenticationFailureHandler((httpServletRequest, httpServletResponse, e) -> {
            log.info("Authentication is failed : {}", e.getMessage());
            httpServletResponse.sendError(HttpServletResponse.SC_CONFLICT);
        });
        return filter;
    }

}