package com.hhc.blogs.mapper;

import com.hhc.blogs.domain.Circle;
import com.hhc.blogs.req.CircleUpdateReq;

import java.util.List;

public interface CircleMapper {

    List<Circle> getCircleList();

    Circle selectCircleByCircleName(String circleName);

    void insertCircle(Circle circle);

    void updateCircle(CircleUpdateReq circleUpdateReq);

    void deleteCircle(Long circleId);

    List<Circle> selectCirclesByManagerId(Long userId);

}
