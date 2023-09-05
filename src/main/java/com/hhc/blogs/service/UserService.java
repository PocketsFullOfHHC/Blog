package com.hhc.blogs.service;

import com.hhc.blogs.domain.User;
import com.hhc.blogs.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<User> list(){
        return userMapper.selectByExample(null);
    }
}
