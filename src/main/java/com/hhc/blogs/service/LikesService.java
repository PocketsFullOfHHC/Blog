package com.hhc.blogs.service;

import com.hhc.blogs.domain.Likes;
import com.hhc.blogs.exception.BusinessException;
import com.hhc.blogs.exception.BusinessExceptionCode;
import com.hhc.blogs.mapper.LikesMapperCust;
import com.hhc.blogs.req.LikesSaveReq;
import com.hhc.blogs.resp.LikesListByBlogResp;
import com.hhc.blogs.resp.LikesListResp;
import com.hhc.blogs.util.CopyUtil;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LikesService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private LikesMapperCust likesMapperCust;

    /**
     * 按时间获取指定用户的点赞信息
     * */
    public List<LikesListResp> list(Long userId){
        List<LikesListResp> likesList = likesMapperCust.getLikesList(userId);
        return likesList;
    }

    /**
     * 按时间获取指定博客的点赞信息
     * */
    public List<LikesListByBlogResp> listByBlog(Long blogId){
        List<LikesListByBlogResp> likesList = likesMapperCust.getLikesListByBlog(blogId);
        return likesList;
    }

    /**
     * 点赞或点踩操作
     * */
    public void save(LikesSaveReq req){
        LOG.info("开始提交点赞点踩操作：{}", req);
        Likes likes = CopyUtil.copy(req, Likes.class);
        // 判重
        List<Likes> likesRepeat = likesMapperCust.getLikesRepeat(likes);
        if(CollectionUtils.isEmpty(likesRepeat)){
            likes.setId(snowFlake.nextId());
            likes.setCommentTime(new Date());
            likesMapperCust.save(likes);
        } else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }
        LOG.info("提交点赞点踩操作成功");
    }

    /**
     * 获取用户的点赞数
     * */
    public Integer likesNum(Long userId){
        List<LikesListResp> likesList = likesMapperCust.getLikesList(userId);
        return likesList.size();
    }
}
