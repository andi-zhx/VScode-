
package com.gen.springbootserver.controller.bydefault;

import static org.springframework.http.ResponseEntity.ok;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import com.gen.springbootserver.dto.bydefault.GridData;
import com.gen.springbootserver.dto.bydefault.ResponseMessage;
import com.gen.springbootserver.dto.errors.CommonApiException;
import com.gen.springbootserver.mybatis.model.*;
import com.gen.springbootserver.utils.ServiceUtils;
import com.gen.springbootserver.utils.CsvExportUtil;
import com.gen.springbootserver.dto.filter.bydefault.RolesGridFilter;
import com.gen.springbootserver.service.bydefault.*;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.ApiOperation;

@Controller
@ResponseBody
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleService roleService;

    @PostMapping("")
    public ResponseEntity<Role> createroles(@Valid @RequestBody Role role) throws CommonApiException {
        return ok(roleService.create(role));
    }

    @ApiOperation(value = "updateroles", notes = "Button Edit")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateroles(@PathVariable Long id, @Valid @RequestBody Role dto)
            throws CommonApiException {
        Role oldrole = roleService.get(id);
        if (oldrole.getName().equals("ADMIN")) {
            return new ResponseEntity<>("It is impossible to update ADMIN", HttpStatus.BAD_REQUEST);
        }
        if (oldrole.getName().equals("USER")) {
            return new ResponseEntity<>("It is impossible to update USER", HttpStatus.BAD_REQUEST);
        }
        Role model = modelMapper.map(dto, Role.class);
        Role result = modelMapper.map(roleService.update(id, model), Role.class);
        return ok(result);
    }

    @ApiOperation(value = "getroles", notes = "Button Edit function-get")
    @GetMapping("/get/{id}")
    public ResponseEntity<Role> getroles(@PathVariable Long id) throws CommonApiException {
        Role result = modelMapper.map(roleService.get(id), Role.class);
        return ok(result);
    }

    @ApiOperation(value = "deleteroles", notes = "Button Delete")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteroles(Authentication auth, @PathVariable Long id) throws CommonApiException {
        Role oldrole = roleService.get(id);
        if (oldrole.getName().equals("ADMIN")) {
            return new ResponseEntity<>("It is impossible to update ADMIN", HttpStatus.BAD_REQUEST);
        }
        if (oldrole.getName().equals("USER")) {
            return new ResponseEntity<>("It is impossible to update USER", HttpStatus.BAD_REQUEST);
        }
        roleService.delete(id);
        return ok(new ResponseMessage("OK"));
    }

    @ApiOperation(value = "exportroles", notes = "Button many-Export")
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportroles(RolesGridFilter filter) throws Exception {
        filter = filter == null ? new RolesGridFilter() : filter;
        List<Role> list = getrolesDataForGrid(filter, false).getItems();
        return CsvExportUtil.exportcsv("", list);
    }

    @ApiOperation(value = "getrolesDataGrid", notes = "Grid")
    @GetMapping("")
    public ResponseEntity<GridData<Role>> getrolesDataGrid(RolesGridFilter filter) throws Exception {
        filter = filter == null ? new RolesGridFilter() : filter;
        return ok(getrolesDataForGrid(filter, true));
    }

    private GridData<Role> getrolesDataForGrid(RolesGridFilter filter, Boolean ispage) throws Exception {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        HashMap<String, String> map = new HashMap<>();
        Field[] fileds = filter.getClass().getDeclaredFields();
        for (int i = 0; i < fileds.length; i++) {
            String Columnname = ServiceUtils.dealFilterfileName("RoleMapper", fileds[i].getName());
            Object ob = ServiceUtils.getGetMethod(filter, fileds[i].getName());
            if (ob != null) {
                map.put(Columnname, ob.toString());
            }
        }
        criteria.andMultiseriateOrLike(map);
        example.setOrderByClause(ServiceUtils.dealsortString("RoleMapper", filter.getSortBy(), filter.getOrderBy()));
        long totalCount = roleService.getListCount(example);
        if (ispage) {
            example.setlimitStart(ServiceUtils.getlimitStart(filter.getPageNumber(), filter.getPageSize()));
            example.setlimitEnd(ServiceUtils.getlimitEnd(filter.getPageSize()));
        }
        return parserolesPageToGridData(roleService.getList(example), totalCount);
    }

    private GridData<Role> parserolesPageToGridData(List<Role> orderList, Long totalCount) {
        GridData<Role> gridData = new GridData<>();
        gridData.setItems(orderList);
        gridData.setTotalCount(totalCount);
        return gridData;
    }

}