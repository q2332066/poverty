package com.cloudera.poverty.controlller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.entity.admin.Role;
import com.cloudera.poverty.entity.admin.UserRole;
import com.cloudera.poverty.service.IRoleService;
import com.cloudera.poverty.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  角色控制器
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;
    @Autowired
    IUserRoleService userRoleService;

    @GetMapping("/{id}")
    public Lay findOne(@PathVariable String id){
        return Lay.ok().data(this.roleService.findById(id));
    }

    @GetMapping("/list")
    public Lay list(@RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(name = "limit", defaultValue = "10") Integer pageSize){
        Page<Role> page = new Page<>(pageNumber, pageSize);
        return Lay.ok().code(0).data(this.roleService.page(page));
    }

    @PostMapping("/add")
    public Lay add(@RequestBody Role entity){
        return Lay.ok().data(this.roleService.saveOrUpdate(entity));
    }

    @PostMapping("/update")
    public Lay update(@RequestBody Role entity){
        return Lay.ok().data(this.roleService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public Lay delete(@PathVariable String id){
        return Lay.ok().data(this.roleService.removeById(id));
    }

    @PostMapping("/add/user/{id}")
    public Lay addUser(@RequestBody List<UserRole> data, @PathVariable String id){
        this.userRoleService.remove(new QueryWrapper<UserRole>().eq("role_id", id));
        return Lay.ok().data(this.userRoleService.saveBatch(data));
    }
}
