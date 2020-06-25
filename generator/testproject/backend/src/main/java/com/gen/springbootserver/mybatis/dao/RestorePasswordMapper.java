package com.gen.springbootserver.mybatis.dao;

import com.gen.springbootserver.mybatis.model.RestorePassword;
import com.gen.springbootserver.mybatis.model.RestorePasswordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RestorePasswordMapper {
    long countByExample(RestorePasswordExample example);

    int deleteByExample(RestorePasswordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RestorePassword record);

    int insertSelective(RestorePassword record);

    List<RestorePassword> selectByExample(RestorePasswordExample example);

    RestorePassword selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RestorePassword record, @Param("example") RestorePasswordExample example);

    int updateByExample(@Param("record") RestorePassword record, @Param("example") RestorePasswordExample example);

    int updateByPrimaryKeySelective(RestorePassword record);

    int updateByPrimaryKey(RestorePassword record);
}