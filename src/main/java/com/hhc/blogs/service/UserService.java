package com.hhc.blogs.service;

import com.hhc.blogs.domain.User;
import com.hhc.blogs.domain.UserExample;
import com.hhc.blogs.mapper.UserMapper;
import com.hhc.blogs.req.UserReq;
import com.hhc.blogs.resp.UserResp;
import com.hhc.blogs.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<UserResp> list(UserReq req){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        List<User> userList = userMapper.selectByExample(userExample);

        // 循环写入封装的返回参数
//        List<UserResp> respList = new ArrayList<>();
//        for(User user : userList){
//            // UserResp userResp = new UserResp();
//            // BeanUtils.copyProperties(user, userResp);
//            // 对象复制
//            UserResp userResp = CopyUtil.copy(user, UserResp.class);
//            respList.add(userResp);
//        }

        // 将User格式的list转化为UserResp格式的list并返回出去
        List<UserResp> list = CopyUtil.copyList(userList, UserResp.class);
        return list;
    }
}
