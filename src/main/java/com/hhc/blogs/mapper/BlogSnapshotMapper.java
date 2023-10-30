package com.hhc.blogs.mapper;

import com.hhc.blogs.domain.BlogSnapshot;
import com.hhc.blogs.domain.BlogSnapshotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlogSnapshotMapper {
    long countByExample(BlogSnapshotExample example);

    int deleteByExample(BlogSnapshotExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BlogSnapshot record);

    int insertSelective(BlogSnapshot record);

    List<BlogSnapshot> selectByExample(BlogSnapshotExample example);

    BlogSnapshot selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BlogSnapshot record, @Param("example") BlogSnapshotExample example);

    int updateByExample(@Param("record") BlogSnapshot record, @Param("example") BlogSnapshotExample example);

    int updateByPrimaryKeySelective(BlogSnapshot record);

    int updateByPrimaryKey(BlogSnapshot record);
}