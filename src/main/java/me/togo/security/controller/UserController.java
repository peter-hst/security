package me.togo.security.controller;

import lombok.extern.slf4j.Slf4j;
import me.togo.security.domain.User;
import me.togo.security.vo.Json;
import me.togo.security.vo.LoginVo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private static final String  SESSION_KEY ="SPRING_SECURITY_CONTEXT";

    @GetMapping("/getSession")
    public Object getSession(HttpSession session){
        log. debug("-----session--- {}" ,session.getAttributeNames());
        return session.getAttribute(SESSION_KEY);
    }
}
