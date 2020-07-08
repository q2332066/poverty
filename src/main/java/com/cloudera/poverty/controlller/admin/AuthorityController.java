package com.cloudera.poverty.controlller.admin;

import com.alibaba.fastjson.JSON;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.vo.AuthVo;
import com.cloudera.poverty.service.AuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/min/est/Authority")
@Api(description = "权限管理")
@CrossOrigin //跨域
@Slf4j
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;

        @ApiOperation("查询所有权限")
        @RequestMapping(value = "selectAuthority",method = RequestMethod.GET,name="API-SELECT")
        public Lay selectAuth(){
        List<AuthVo> list= authorityService.getList();
        return Lay.ok().data(list);
    }

    @ApiOperation("通过roldid加载权限")
    @RequestMapping(value = "selectAuthorityByRoleId",method = RequestMethod.GET,name="API-AUTH")
    public Lay selectAuthByRoleId(@RequestParam String roleId){
        List<AuthVo> list= authorityService.getAuthorityByid(roleId);
        return Lay.ok().data(list);
    }



    @ApiOperation("禁用权限")
    @RequestMapping(value = "updateAuthority",method = RequestMethod.GET,name="API-AUTH")
    public Lay update(@RequestParam String roleId,@RequestParam String authIds){
        List<String> array=JSON.parseArray(authIds,String.class);
        authorityService.updateAuthorityByRoleId(roleId,array);

        return Lay.ok().msg("禁用");

    }




}
