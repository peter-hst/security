package me.togo.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "Hello Security";
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sessionId")
    public String sessionId(HttpSession session) {
        return session.getId();
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello, World!";
    }
}
