package com.hhc.blogs.controller;

import com.hhc.blogs.domain.Test;
import com.hhc.blogs.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "hello world post！" + name;
    }

    @GetMapping("/test/list1")
    public List<Test> list(){
        return testService.list();
    }
}
