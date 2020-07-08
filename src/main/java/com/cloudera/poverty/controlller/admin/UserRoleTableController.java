package com.cloudera.poverty.controlller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.result.R;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.entity.admin.RolePermissionRelationshipTable;
import com.cloudera.poverty.entity.admin.RoleTable;
import com.cloudera.poverty.entity.admin.UserRoleRelationshipTable;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.service.UserRoleService;
import com.cloudera.poverty.service.UserTabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/min/est/userrole")
@Api(description = "账号角色管理")
@CrossOrigin //跨域
@Slf4j
public class UserRoleTableController {

    @Autowired
    private UserRoleService userRoleService;

    @ApiOperation("修改添加账号角色")
    @RequestMapping(value = "role",method = RequestMethod.POST,name = "API-USER")
    @Transactional
    public Lay deleteUserRole(
            @RequestParam List<UserRoleRelationshipTable> uRTableList
    ){
        for (int i = 0; i < uRTableList.size(); i++) {
            UserRoleRelationshipTable userRoleRelationshipTable =  uRTableList.get(i);
            QueryWrapper<UserRoleRelationshipTable> wrapper=new QueryWrapper<>();
            wrapper.eq("user_id",userRoleRelationshipTable.getId());
            userRoleService.removeById(wrapper);
            break;
        }
        for (int i = 0; i < uRTableList.size(); i++) {
            UserRoleRelationshipTable userRoleRelationshipTable =  uRTableList.get(i);
            userRoleService.save(userRoleRelationshipTable);
        }
        return Lay.ok().msg("成功");
    }
    /**
     * 账号角色查询
     * @param uid
     * @return
     */
    @ApiOperation("查询账号角色")
    @RequestMapping(value = "role",method = RequestMethod.GET,name = "API-USER")
    public Lay selectRole(String uid){
        List<RoleTable> list=userRoleService.selectRole(uid);
        if (null==list){
            return Lay.ok().msg("账号暂无角色");
        }
        return Lay.ok().count((long) list.size()).data(list);
    }

}
