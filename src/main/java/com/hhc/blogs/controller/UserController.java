package com.hhc.blogs.controller;

import com.hhc.blogs.domain.User;
import com.hhc.blogs.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/user/list")
    public List<User> list(){
        return userService.list();
    }
}
