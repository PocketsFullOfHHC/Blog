package com.hhc.blogs.service;

import com.hhc.blogs.domain.Collect;
import com.hhc.blogs.exception.BusinessException;
import com.hhc.blogs.exception.BusinessExceptionCode;
import com.hhc.blogs.mapper.CollectMapper;
import com.hhc.blogs.req.CollectSaveReq;
import com.hhc.blogs.resp.BlogListResp;
import com.hhc.blogs.util.CopyUtil;
import com.hhc.blogs.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectService {
    private static final Logger LOG = LoggerFactory.getLogger(CollectService.class);

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private BlogService blogService;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 收藏
     * */
    public void save(CollectSaveReq req){
        LOG.info("req:{}",req);
        Collect collect = CopyUtil.copy(req, Collect.class);
        Collect collectByUserAndBlog = collectMapper.getCollectByUserIdAndBlogId(req.getCollectorId(),req.getBlogId());
        if (ObjectUtils.isEmpty(collectByUserAndBlog)){
            long collectId = snowFlake.nextId();
            collect.setId(collectId);
            collect.setCollectTime(new Date());
            collectMapper.save(collect);
        }else {
            throw new BusinessException(BusinessExceptionCode.COLLECT_REPEAT);
        }
    }

    /**
     * 取消收藏
     * */
    public void deleteByUserIdAndBlogId(Long userId, Long blogId){
        collectMapper.deleteByUserIdAndBlogId(userId, blogId);
    }

    /**
     * 查询用户收藏的全部博客
     * */
    public List<BlogListResp> collectBlogList(Long userId){
        List<Collect> collects = collectMapper.getCollectListByUser(userId);
        List<BlogListResp> collectBlogList = new ArrayList<>();
        for (Collect collect : collects) {
            BlogListResp blogById = blogService.getBlogById(collect.getBlogId());
            collectBlogList.add(blogById);
        }
        return collectBlogList;
    }

    /**
     * 删除某篇博客的全部收藏
     * */
    public void deleteByBlogId(Long blogId){
        LOG.info("删除帖子{}收藏",blogId);
        collectMapper.deleteByBlogId(blogId);
    }
}
