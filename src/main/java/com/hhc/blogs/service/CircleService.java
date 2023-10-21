package com.hhc.blogs.service;

import com.hhc.blogs.domain.Circle;
import com.hhc.blogs.domain.User;
import com.hhc.blogs.exception.BusinessException;
import com.hhc.blogs.exception.BusinessExceptionCode;
import com.hhc.blogs.mapper.CircleMapper;
import com.hhc.blogs.mapper.UserCircleMapper;
import com.hhc.blogs.req.CircleCreateReq;
import com.hhc.blogs.req.CircleUpdateReq;
import com.hhc.blogs.util.CopyUtil;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CircleService {

    private static final Logger LOG = LoggerFactory.getLogger(CircleService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private CircleMapper circleMapper;

    @Resource
    private UserCircleMapper userCircleMapper;

    /**
     * 查询全部部落信息
     * */
    public List<Circle> list(){
        List<Circle> circle = circleMapper.getCircleList();
        return circle;
    }

    /**
     * 创建新部落
     * */
    public void createCircle(CircleCreateReq req){
        Circle circle = CopyUtil.copy(req, Circle.class);
        LOG.info("创建新部落{}", req.getCircleName());
        Circle circleDb = circleMapper.selectCircleByCircleName(req.getCircleName());
        if (ObjectUtils.isEmpty(circleDb)){
            circle.setId(snowFlake.nextId());
            circle.setCreatedTime(new Date());
            circleMapper.insertCircle(circle);
        }else {
            throw new BusinessException(BusinessExceptionCode.CIRCLE_NAME_EXIST);
        }
    }

    /**
     * 更新部落信息
     * */
    public void updateCircle(CircleUpdateReq circle){
        LOG.info("更新部落信息：{}", circle);
        circleMapper.updateCircle(circle);
    }

    /**
     * 删除部落
     * */
    public void deleteCircle(Long circleId){
        circleMapper.deleteCircle(circleId);
    }

    /**
     * 获取我创建的部落
     * */
    public List<Circle> myCreatedCircle(Long userId){
        List<Circle> circles = circleMapper.selectCirclesByManagerId(userId);
        return circles;
    }

    /**
     * 加入部落
     * */
    public void joinCircle(Long userId, Long circleId, Long managerId){
        Circle circle = userCircleMapper.selectCircleByUserIdAndCircleId(userId, circleId);
        if (userId.equals(managerId)){
            throw new BusinessException(BusinessExceptionCode.JOINED_CIRCLE_ERROR);
        }
        if (ObjectUtils.isEmpty(circle)){
            userCircleMapper.insert(snowFlake.nextId(), userId, circleId, managerId);
        }else {
            throw new BusinessException(BusinessExceptionCode.JOINED_CIRCLE_REPEAT);
        }
    }

    /**
     * 退出部落
     * */
    public void exitCircle(Long userId, Long circleId){
        userCircleMapper.delete(userId, circleId);
    }

    /**
     * 删除指定部落里的全部成员
     * */
    public void deleteMembersFromCircle(Long circleId){
        userCircleMapper.deleteByCircleId(circleId);
    }

    /**
     * 查询指定人加入的全部部落
     * */
    public List<Circle> myJoinedCircle(Long userId){
        List<Circle> circles = userCircleMapper.selectCirclesByUserId(userId);
        return circles;
    }

    /**
     * 获取部落里的全部成员
     * */
    public List<User> circleMember(Long circleId){
        List<User> member = userCircleMapper.getMemberByCircleId(circleId);
        return member;
    }

}
