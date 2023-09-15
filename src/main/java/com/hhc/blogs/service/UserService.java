package com.hhc.blogs.service;

import com.hhc.blogs.domain.User;
import com.hhc.blogs.domain.UserExample;
import com.hhc.blogs.exception.BusinessException;
import com.hhc.blogs.exception.BusinessExceptionCode;
import com.hhc.blogs.mapper.UserMapper;
import com.hhc.blogs.mapper.UserMapperCust;
import com.hhc.blogs.req.UserLoginReq;
import com.hhc.blogs.req.UserReq;
import com.hhc.blogs.req.UserSignUpReq;
import com.hhc.blogs.resp.UserLoginResp;
import com.hhc.blogs.resp.UserResp;
import com.hhc.blogs.resp.UserSignUpResp;
import com.hhc.blogs.util.CopyUtil;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private UserMapperCust userMapperCust;

    /**
     * 测试查询所有的用户信息
     * */
    public List<UserResp> list(UserReq req){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        List<User> userList = userMapper.selectByExample(userExample);

        // 将User格式的list转化为UserResp格式的list并返回出去
        List<UserResp> list = CopyUtil.copyList(userList, UserResp.class);
        return list;
    }

    /**
     * 用户注册
     * */
    public UserSignUpResp SignUp(UserSignUpReq userSignUpReq){
        // 拷贝请求参数以便于设置id
        User user = CopyUtil.copy(userSignUpReq, User.class);
        LOG.info("开始注册用户");
        User userDB = SelectByUsername(user.getUsername());
        // 判断用户是否重复
        if(ObjectUtils.isEmpty(userDB)){
            LOG.info("数据库中不存在同名");
            user.setId(snowFlake.nextId());
            user.setAvatar("./default.png");
            userMapper.insert(user);
            LOG.info("注册成功");
            UserSignUpResp userSignUpResp = CopyUtil.copy(user, UserSignUpResp.class);
            return userSignUpResp;
        } else {
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
    }

    /**
     * 用户登录
     * */
    public UserLoginResp Login(UserLoginReq userLoginReq){
        // 查询用户名
        LOG.info("开始登录");
        User userDB = SelectByUsername(userLoginReq.getUsername());
        if(ObjectUtils.isEmpty(userDB)){
            LOG.info("用户名不存在，{}", userLoginReq.getUsername());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if(userDB.getPassword().equals(userLoginReq.getPassword())){
                LOG.info("登录成功");
                UserLoginResp userLoginResp = CopyUtil.copy(userDB, UserLoginResp.class);
                return userLoginResp;
            } else {
                LOG.info("密码错误，输入密码：{}，数据库密码：{}", userLoginReq.getPassword(), userDB.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

    /**
     * 根据姓名查找用户
     * */
    public User SelectByUsername(String username){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        // 实体类字段Username等于username值
        criteria.andUsernameEqualTo(username);
        // 用户名唯一，最多只能查到一条，但mybatis只能用list接收
        List<User> userList = userMapper.selectByExample(userExample);
        // 判断list是否为空
        if(CollectionUtils.isEmpty(userList)){
            return null;
        } else {
            // 返回第一条(唯一一条)
            return userList.get(0);
        }
    }

    /**
     * 修改图片路径
     * */
    public void alterAvatarName(String avatarName, String userId){
        userMapperCust.alterAvatarName(avatarName, Long.parseLong(userId));
    }
}
