package com.cloudera.poverty.controlller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.result.R;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.admin.RolePermissionRelationshipTable;
import com.cloudera.poverty.entity.admin.UserRoleRelationshipTable;
import com.cloudera.poverty.entity.vo.AuthVo;
import com.cloudera.poverty.service.RoleAuthorityService;
import com.cloudera.poverty.service.UserRoleService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/min/est/roleauthority")
@Api(description = "角色权限管理")
@CrossOrigin //跨域
@Slf4j
public class RoleAuthorityController {
    @Autowired
    private RoleAuthorityService roleAuthorityService;

    @Autowired
    private UserRoleService userRoleService;


    @ApiOperation("查询角色拥有权限")
    @RequestMapping(value = "select",method = RequestMethod.GET,name = "API-ROLE")
    public Lay selectRoleAut(@RequestParam String roleId){
        List<AuthVo> list= roleAuthorityService.selectList(roleId);
        return Lay.ok().count((long) list.size()).data(list);
    }


    @ApiOperation("账号菜单栏权限")
    @RequestMapping(value = "selectAuth",method = RequestMethod.GET,name = "API-SELECT")
    public Lay selectAuth(HttpServletRequest request){
        Claims claims = JwtUtils.getMemberIdByJwtToken(request);
        String uId = (String) claims.get("uid");
        QueryWrapper<UserRoleRelationshipTable> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",uId);
        List<UserRoleRelationshipTable> list = userRoleService.list(wrapper);
        List<AuthVo> auth=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String roleId = list.get(i).getRoleId();
            List<AuthVo> authVos = roleAuthorityService.selectList(roleId);
                auth.addAll(authVos);
        }
        return Lay.ok().data(auth);
    }

    /**
     * 修改角色权限
     */
    @ApiOperation("修改添加角色拥有权限")
    @RequestMapping(value = "save",method = RequestMethod.GET,name = "API-ROLE")
    @Transactional
    public Lay deleteRoleAut(@RequestBody List<RolePermissionRelationshipTable> roleList){

        for (int i = 0; i < roleList.size(); i++) {
            RolePermissionRelationshipTable rolePermissionRelationshipTable =  roleList.get(i);
            QueryWrapper<RolePermissionRelationshipTable> wrapper=new QueryWrapper<>();
            wrapper.eq("role_id",rolePermissionRelationshipTable.getId());
            break;
        }
        for (int i = 0; i < roleList.size(); i++) {
            RolePermissionRelationshipTable rolePermissionRelationshipTable =  roleList.get(i);
        roleAuthorityService.save(rolePermissionRelationshipTable);
        }
        return Lay.ok().msg("修改成功");
    }
}
