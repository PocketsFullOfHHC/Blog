package com.hhc.blogs.mapper;
import org.apache.ibatis.annotations.Param;

public interface UserMapperCust {
    public void alterAvatarName(@Param("avatarName")String avatarName, @Param("userId")Long userId);
    public void updateName(Long id, String name);
}
