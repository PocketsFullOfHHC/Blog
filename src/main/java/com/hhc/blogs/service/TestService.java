package com.hhc.blogs.service;

import com.hhc.blogs.domain.Test;
import com.hhc.blogs.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.selectByExample(null);
    }
}
