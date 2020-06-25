package com.gen.springbootserver.mybatis.dao;

import com.gen.springbootserver.mybatis.model.Image;

public interface ImageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKeyWithBLOBs(Image record);
}