package me.togo.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.togo.security.domain.User;
import me.togo.security.service.UserServiceI;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义JSON形式登录逻辑
 */

@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final UserServiceI userService;

    public LoginFilter(String defaultFilterProcessesUrl, UserServiceI userService) {
        super(defaultFilterProcessesUrl);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final JSONObject jsonObject = getRequestBody(request);
        final String username = jsonObject.getString("username");
        final String password = jsonObject.getString("password");
        log.debug("--------> login filter attemptAuthentication user:{}, pwd:{}", username, password);
        User user = validateUsernameAndPassword(username, password);
        log.debug("--------- user: {}", user);
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = Arrays.asList("USER", "ADMIN", "DEV").stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, simpleGrantedAuthorities);
        return authentication;
    }

    /**
     * 获取请求体
     */
    private JSONObject getRequestBody(HttpServletRequest request) throws AuthenticationException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = request.getInputStream();
            byte[] bs = new byte[StreamUtils.BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                stringBuilder.append(new String(bs, 0, len));
            }
            return JSON.parseObject(stringBuilder.toString());
        } catch (IOException e) {
            log.error("get request body error.");
        }
        throw new AuthenticationServiceException("invalid request body");
    }

    /**
     * 校验用户名和密码
     */
    private User validateUsernameAndPassword(String username, String password) throws AuthenticationException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not exist");
        }
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationServiceException("error username or password");
        }
        return user;
    }

}
