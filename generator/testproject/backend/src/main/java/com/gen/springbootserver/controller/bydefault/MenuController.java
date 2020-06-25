package com.gen.springbootserver.controller.bydefault;
import com.gen.springbootserver.mybatis.model.Menu;
import com.gen.springbootserver.mybatis.model.MenuExample;
import com.gen.springbootserver.mybatis.model.Role;
import com.gen.springbootserver.service.bydefault.GGUserDetailsService;
import com.gen.springbootserver.service.bydefault.MenuService;
import com.gen.springbootserver.service.bydefault.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gen.springbootserver.mybatis.model.User;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService mapper;
    @Autowired
    UserService usermapper;
    @GetMapping("/getmenu")
    public ResponseEntity<List<Menu>> getMenu(Authentication auth) {
        User curuser = ((GGUserDetailsService.GGUserDetails) auth.getPrincipal()).getUser();
        List<Role> roles = curuser.getRoles().stream().filter((Role role) -> role.getName().equals("ADMIN")).collect(Collectors.toList());
        List<Menu> menus;
        if (roles.isEmpty()) {
            menus = usermapper.getuserMenus(curuser.getId());
            menus = menus.stream().filter((Menu menu) -> menu.getHidden().equals(false)
                    && menu.getIsDelete().equals(false)).collect(Collectors.toList());
        } else {
            MenuExample example = new MenuExample();
            example.createCriteria().andHiddenEqualTo(false).andIsDeleteEqualTo(false);
            menus = mapper.getList(example);
        }
        return ok(menus);
    }

    @GetMapping("/gettypemenu/{type}")
    public ResponseEntity<List<Menu>> getTypeMenu(Authentication auth, @PathVariable String type) {
        User curuser = ((GGUserDetailsService.GGUserDetails) auth.getPrincipal()).getUser();
        List<Role> roles = curuser.getRoles().stream().filter((Role role) -> role.getName().equals("ADMIN"))
                .collect(Collectors.toList());
        List<Menu> menus;
        if (roles.isEmpty()) {
            menus = usermapper.getuserMenus(curuser.getId());
            menus = menus.stream().filter((Menu menu) -> menu.getHidden().equals(false)
                    && menu.getIsDelete().equals(false) && menu.getType().equals(type)).collect(Collectors.toList());
        } else {
            MenuExample example = new MenuExample();
            example.createCriteria().andHiddenEqualTo(false).andIsDeleteEqualTo(false).andTypeEqualTo(type);
            menus = mapper.getList(example);
        }
        return ok(menus);
    }


    @GetMapping("/getchildmenu/{parentname}")
    public ResponseEntity<List<Menu>> getChildMenu(Authentication auth, @PathVariable String parentname) {
        User curuser = ((GGUserDetailsService.GGUserDetails) auth.getPrincipal()).getUser();
        List<Role> roles = curuser.getRoles().stream().filter((Role role) -> role.getName().equals("ADMIN"))
                .collect(Collectors.toList());
        List<Menu> menus;
        if (roles.isEmpty()) {
            menus = usermapper.getuserMenus(curuser.getId());
            menus = menus.stream()
                    .filter((Menu menu) -> menu.getHidden().equals(false) && menu.getIsDelete().equals(false)
                            && menu.getType().equals("menu") && menu.getParentname().equals(parentname))
                    .collect(Collectors.toList());
        } else {
            MenuExample example = new MenuExample();
            example.createCriteria().andHiddenEqualTo(false).andIsDeleteEqualTo(false).andTypeEqualTo("menu")
                    .andParentnameEqualTo(parentname);
            menus = mapper.getList(example);
        }
        return ok(menus);
    }
}