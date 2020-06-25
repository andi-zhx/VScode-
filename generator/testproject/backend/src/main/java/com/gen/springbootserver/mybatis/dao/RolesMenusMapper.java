package com.gen.springbootserver.mybatis.dao;

import com.gen.springbootserver.mybatis.model.RolesMenus;
import com.gen.springbootserver.mybatis.model.RolesMenusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolesMenusMapper {
    long countByExample(RolesMenusExample example);

    int deleteByExample(RolesMenusExample example);

    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int insert(RolesMenus record);

    int insertSelective(RolesMenus record);

    List<RolesMenus> selectByExample(RolesMenusExample example);

    RolesMenus selectByPrimaryKey(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int updateByExampleSelective(@Param("record") RolesMenus record, @Param("example") RolesMenusExample example);

    int updateByExample(@Param("record") RolesMenus record, @Param("example") RolesMenusExample example);

    int updateByPrimaryKeySelective(RolesMenus record);

    int updateByPrimaryKey(RolesMenus record);
}