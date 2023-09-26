package com.hhc.blogs.service;

import com.hhc.blogs.domain.Follow;
import com.hhc.blogs.exception.BusinessException;
import com.hhc.blogs.exception.BusinessExceptionCode;
import com.hhc.blogs.mapper.FollowMapper;
import com.hhc.blogs.mapper.FollowMapperCust;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class FollowService {

    private static final Logger LOG = LoggerFactory.getLogger(FollowService.class);

    @Resource
    private FollowMapper followMapper;

    @Resource
    private FollowMapperCust followMapperCust;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private UserService userService;

    /**
     * 关注
     * */
    public Follow follow(String likeId, String beLikedId){
        Follow follow1 = followMapperCust.ifFollowed(Long.parseLong(likeId), Long.parseLong(beLikedId));
        if (ObjectUtils.isEmpty(follow1)){
            LOG.info("{}关注{}", likeId, beLikedId);
            Follow follow = new Follow(snowFlake.nextId(), Long.parseLong(likeId), Long.parseLong(beLikedId), new Date());
            followMapper.insert(follow);
            LOG.info("关注成功");
            return follow;
        }else {
            throw new BusinessException(BusinessExceptionCode.FOLLOW_REPEAT);
        }
    }

    /**
     * 取关
     * */
    public void unFollow(String likeId, String beLikedId){
        followMapperCust.unFollow(Long.parseLong(likeId), Long.parseLong(beLikedId));
        LOG.info("取关成功");
    }

    /**
     * 查询是否被关注
     * */
    public Follow ifFollowed(String likeId, String beLikedId){
        Follow follow = followMapperCust.ifFollowed(Long.parseLong(likeId), Long.parseLong(beLikedId));
        return follow;
    }
}
