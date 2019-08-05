package me.togo.security.config;

import lombok.extern.slf4j.Slf4j;
import me.togo.security.filter.*;
import me.togo.security.interceptor.WebInterceptor;
import me.togo.security.service.UserServiceI;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configurable
@EnableWebSecurity
@Slf4j
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String LOGIN_URL = "/login";

    private UserServiceI userService;

    public AppSecurityConfig(UserServiceI userService) {
        this.userService = userService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        log.debug("----------------------- AppSecurityConfig - configure - web");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.debug("----------------------- AppSecurityConfig - configure - auth");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser("peter").password(passwordEncoder.encode("123456")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(new MyLogoutSuccessHandler()).clearAuthentication(true).permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(new UnLoginAuthenticationEntryPoint()).accessDeniedHandler(new RejectAccessDeniedHandler());

        http.csrf().disable();
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new WebFilter(), UsernamePasswordAuthenticationFilter.class);
        log.debug("--------------------- AppSecurityConfig - configure - http");
    }

    /**
     * 自定义认证过滤器
     */
    private LoginFilter loginFilter() {
        LoginFilter loginFilter = new LoginFilter(LOGIN_URL, userService);
        loginFilter.setAuthenticationSuccessHandler(new LoginAuthenticationSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new LoginAuthenticationFailureHandler());
        return loginFilter;
    }
}
