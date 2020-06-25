package com.gen.springbootserver.service.bydefault;

import java.util.List;

import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.mybatis.dao.*;
import com.gen.springbootserver.mybatis.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
    @Autowired
    MenuMapper mapper;

    @Transactional
    public Menu create(Menu menu) throws CommonApiException {
        mapper.insert(menu);
        return get(menu.getId());
    }

    @Transactional
    public Menu update(Long id, Menu menu) throws CommonApiException {
        Menu exist = mapper.selectByPrimaryKey(id);
        if (exist == null) {
            throw new CommonApiException("this Menu not exixt");
        }
        mapper.updateByPrimaryKey(menu);
        return get(menu.getId());
    }

    @Transactional
    public void delete(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    public Menu get(Long id) throws CommonApiException {
        Menu menu = mapper.selectByPrimaryKey(id);
        if (menu == null) {
            throw new CommonApiException("this Menu not exixt");
        }
        return menu;
    }

    public List<Menu> getList(MenuExample example) {
        return mapper.selectByExample(example);
    }

    public Long getListCount(MenuExample example) {
        return mapper.countByExample(example);
    }
}