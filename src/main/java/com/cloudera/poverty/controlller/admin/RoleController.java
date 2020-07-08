package com.cloudera.poverty.controlller.admin;

import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.result.R;
import com.cloudera.poverty.entity.admin.RoleTable;
import com.cloudera.poverty.entity.vo.RoleVo;
import com.cloudera.poverty.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/min/est/role")
@Api(description = "角色管理")
@CrossOrigin //跨域
@Slf4j
public class RoleController {


    @Autowired
    private RoleService roleService;

    @ApiOperation("添加账号角色")
    @RequestMapping(value = "save",method = RequestMethod.POST,name = "API-ROLE")
    public Lay saveUserRole(
            @RequestBody RoleTable roleTable){
        Boolean b=roleService.save(roleTable);
        if (b){
            return Lay.ok().msg("添加角色成功");
        }
        return Lay.error().msg("添加角色失败");
    }

    @ApiOperation("全部角色查询")
    @RequestMapping(value = "select",method = RequestMethod.GET,name = "API-ROLE")
    public Lay select() {
        List<RoleTable> list = roleService.list(null);
        return Lay.ok().count((long) list.size()).data(list);
    }
    @ApiOperation("删除账号")
    @RequestMapping(value = "delete",method = RequestMethod.GET,name = "API-USER")
    public Lay removeUser(@RequestParam String uid){
        roleService.removeById(uid);
        return Lay.ok().msg("删除成功");
    }
    @RequestMapping(value = "deletelist",method = RequestMethod.POST,name = "API-ROLE")
    @ApiOperation("删除账号批量")
    @Transactional
    public Lay removeList(@RequestBody Map<String,Object> idList
    ){
        List<String> list = (List<String>) idList.get("ids");
        for (int i = 0; i < list.size(); i++) {
            String o =  list.get(i);
            roleService.removeById(o);
        }
        return Lay.ok().msg("删除成功");
    }

}