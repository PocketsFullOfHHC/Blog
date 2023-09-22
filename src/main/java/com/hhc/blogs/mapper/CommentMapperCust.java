package com.hhc.blogs.mapper;

import com.hhc.blogs.resp.CommentListResp;

import java.util.List;

public interface CommentMapperCust {

    public List<CommentListResp> getCommentList(Long blogId);

}
