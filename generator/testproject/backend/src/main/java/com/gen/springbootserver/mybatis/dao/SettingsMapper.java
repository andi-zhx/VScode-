package com.gen.springbootserver.mybatis.dao;

import com.gen.springbootserver.mybatis.model.Settings;
import com.gen.springbootserver.mybatis.model.SettingsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SettingsMapper {
    long countByExample(SettingsExample example);

    int deleteByExample(SettingsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Settings record);

    int insertSelective(Settings record);

    List<Settings> selectByExample(SettingsExample example);

    Settings selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Settings record, @Param("example") SettingsExample example);

    int updateByExample(@Param("record") Settings record, @Param("example") SettingsExample example);

    int updateByPrimaryKeySelective(Settings record);

    int updateByPrimaryKey(Settings record);
}