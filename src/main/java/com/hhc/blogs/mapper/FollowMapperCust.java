package com.hhc.blogs.mapper;

import com.hhc.blogs.domain.Follow;

import java.util.List;

public interface FollowMapperCust {

    public void unFollow(Long likeId, Long beLikedId);

    public Follow ifFollowed(Long likeId, Long beLikedId);

    public List<Follow> followList(Long likeId);

}
