package com.gen.springbootserver.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gen.springbootserver.mybatis.dao.MenuMapper;
import com.gen.springbootserver.mybatis.model.Menu;
import com.gen.springbootserver.mybatis.model.MenuExample;
import com.gen.springbootserver.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartServer implements ApplicationRunner {
    @Autowired
    MenuMapper mapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JSONObject jsonobject=JsonUtils.getJsonResource("menu");
        if (!jsonobject.getBoolean("save")) {
            dealisdeletemenu();
            importmenu(jsonobject.getJSONArray("data").toJSONString());
            JsonUtils.updateJsonResource("menu");
        }
    }

    private void importmenu(String data) {
        List<Menu> list = (List<Menu>) JSONArray.parseArray(data, Menu.class);
        for (int i = 0; i < list.size(); i++) {
            Menu menu = list.get(i);
            menu.setCreatetime(LocalDateTime.now(ZoneId.systemDefault()));
            MenuExample example = new MenuExample();
            example.createCriteria().andPermissionEqualTo(menu.getPermission());
            List<Menu> emeun = mapper.selectByExample(example);
            if (!emeun.isEmpty()) {
                menu.setIsDelete(false);
                menu.setId(emeun.get(0).getId());
                mapper.updateByExample(menu, example);
            } else {
                mapper.insert(menu);
            }
        }
    }

    private void dealisdeletemenu() {
        MenuExample example = new MenuExample();
        example.createCriteria().andIsDefaultEqualTo(false);
        example.createCriteria().andIsDeleteEqualTo(false);
        List<Menu> emeun = mapper.selectByExample(example);
        for (int i = 0; i < emeun.size(); i++) {
            Menu menu = emeun.get(i);
            menu.setIsDelete(true);
            mapper.updateByPrimaryKey(menu);
        }
    }
}