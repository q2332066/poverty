package com.cloudera.poverty.controlller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.admin.Authorization;
import com.cloudera.poverty.service.IAuthorizationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/min/est/roleauthority")
public class AuthController {

    @Autowired
    IAuthorizationService authorizationService;

    @GetMapping("/tree")
    public Lay findTree(String parent){
        return Lay.ok().code(200).data(this.authorizationService.findTree(parent));
    }

    @GetMapping("/list")
    public Lay list(@RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(name = "limit", defaultValue = "10") Integer pageSize){
        Page<Authorization> page = new Page<>(pageNumber, pageSize);
        return Lay.ok().data(this.authorizationService.page(page));
    }

    @PostMapping("/add")
    public Lay add(@RequestBody Authorization entity){
        return Lay.ok().data(this.authorizationService.saveOrUpdate(entity));
    }

    @PostMapping("/update")
    public Lay update(@RequestBody Authorization entity){
        return Lay.ok().data(this.authorizationService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public Lay delete(@PathVariable String id){
        return Lay.ok().data(this.authorizationService.removeById(id));
    }

    @GetMapping("/selectAuth")
    public Lay menu(HttpServletRequest request){
        Claims claims= JwtUtils.getMemberIdByJwtToken(request);
        return Lay.ok().data(this.authorizationService.getMenus((String) claims.get("uid")));
    }
}
