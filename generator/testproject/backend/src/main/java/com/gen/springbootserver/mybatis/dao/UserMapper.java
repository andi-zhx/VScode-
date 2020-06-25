package com.gen.springbootserver.mybatis.dao;

import com.gen.springbootserver.mybatis.model.Menu;
import com.gen.springbootserver.mybatis.model.Role;
import com.gen.springbootserver.mybatis.model.User;
import com.gen.springbootserver.mybatis.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<Menu> getuserMenus(Long id);

    List<Role> getuserRoles(Long id);
}