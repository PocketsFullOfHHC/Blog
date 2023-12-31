package com.hhc.blogs.mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperCust {
    public void alterAvatarName(@Param("avatarName")String avatarName, @Param("userId")Long userId);
    public void updateName(Long id, String name);
    public Long getUserIdByUserName(String username);
    public Long getUserIdByName(String name);
    public List<Long> getUserInfoList(String tag);
    public List<Long> getUserIdByTag(String tag);
}
