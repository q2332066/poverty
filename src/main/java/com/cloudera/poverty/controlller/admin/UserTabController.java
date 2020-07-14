package com.cloudera.poverty.controlller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.result.R;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.admin.UserRole;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.entity.vo.*;
import com.cloudera.poverty.service.IRoleService;
import com.cloudera.poverty.service.IUserRoleService;
import com.cloudera.poverty.service.UserTabService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/min/est/user")
@Api(description = "账号管理")
@CrossOrigin //跨域
@Slf4j
public class UserTabController {

    @Autowired
    private UserTabService userTabService;
    @Autowired
    IUserRoleService userRoleService;
    @Autowired
    IRoleService roleService;

    /**
     * 添加账号
     * @param userTable
     * @return
     */
    @ApiOperation("添加账号")
    @RequestMapping(value = "save",method = RequestMethod.POST,name = "API-USER")
    public Lay saveUser(
            @RequestBody UserTableVo userTable){
        String username = userTable.getUserName();
        String password = userTable.getPassword();
        password = new Md5Hash(password, username, 3).toString();
        userTable.setPassword(password);
        String msg=userTabService.saveUser(userTable);
        return Lay.ok().msg(msg);
    }

    @ApiOperation("删除账号")
    @RequestMapping(value = "delete",method = RequestMethod.GET,name = "API-USER")
    public Lay removeUser(@RequestParam String uid){
        userTabService.removeById(uid);
        return Lay.ok().msg("删除成功");
    }
    @RequestMapping(value = "deletelist",method = RequestMethod.POST,name = "API-USER")
    @ApiOperation("删除账号批量")
    @Transactional
    public Lay removeList(@RequestBody Map<String,Object> idList
    ){
        List<String> list = (List<String>) idList.get("ids");
        for (int i = 0; i < list.size(); i++) {
            String o =  list.get(i);
            userTabService.removeById(o);
        }
        return Lay.ok().msg("删除成功");
    }

    @ApiOperation("禁用账号")
    @RequestMapping(value = "remove",method = RequestMethod.POST,name = "API-USER")
    public Lay removeById(@RequestParam String uid){
        boolean b = userTabService.removeUser(uid);
        if (b){
            return Lay.ok().msg("账号已解除禁用");
        }
        return Lay.ok().msg("账号已禁用");
    }
    @ApiOperation("重置账号")
    @RequestMapping(value = "update",method = RequestMethod.POST,name = "API-USER")
    public Lay update(@RequestParam String uid){
        UserTable userTable = userTabService.getById(uid);
        String username = userTable.getUserName();
        String password="123456";
        password = new Md5Hash(password, username, 3).toString();
        userTable.setPassword(password);
        userTabService.updateById(userTable);
        return Lay.ok().msg("重置成功");
    }
    @ApiOperation("查询全部账号")
    @RequestMapping(value = "select",method = RequestMethod.POST,name = "API-USER")
    public Lay select(){
        List<UserTableVo> list=userTabService.selectList();
        int count = userTabService.count();
        return Lay.ok().count((long) count).data(list);
    }
    @ApiOperation("登录账号")
    @RequestMapping(value = "login",method = RequestMethod.POST,name = "API-SELECT")
    public R login(
            @RequestBody LoginVo loginVo, HttpServletResponse response, HttpSession session){
         String token=userTabService.login(loginVo);
        session.setAttribute("token",token);
         response.setHeader("token",token);
         return R.ok().data("token",token);
    }
    @ApiOperation(value = "根据token获取登录信息")
    @RequestMapping(value = "get-login-info",method = RequestMethod.GET,name = "API-SELECT")
    public R getLoginInfo(HttpServletRequest request){
//        try{
            Claims claims = JwtUtils.getMemberIdByJwtToken(request);
            String uid = (String) claims.get("uid");
            String showname = (String) claims.get("showname");
            String regional = (String) claims.get("regional");
            String level = (String) claims.get("level");
            String did = (String) claims.get("did");
            String tid = (String) claims.get("tid");
            String rid = (String) claims.get("rid");
            UserTable userTable = new UserTable();
            userTable.setUId(uid);
            userTable.setShowName(showname);
            userTable.setRegionalId(regional);
            userTable.setLevel(level);
            userTable.setDid(did);
            userTable.setTid(tid);
            userTable.setRid(rid);
            return R.ok().data("userInfo", userTable);
//        }catch (Exception e){
//            log.error("解析用户信息失败，" + e.getMessage());
//            throw new PaException(ResultCodeEnum.FETCH_USERINFO_ERROR);
//        }
    }

    @ApiOperation("账号角色权限")
    @RequestMapping(value = "selectAll",method = RequestMethod.GET,name = "API-SELECT")
    public Lay selectAll(@RequestParam String uId){
        UserVo userVo = userTabService.selectAll(uId);
        return Lay.ok().data(userVo);
    }




    @ApiOperation("修改账号")
    @RequestMapping(value = "updateuser",method = RequestMethod.POST,name = "API-SELECT")
    public Lay updateUser(
            @RequestBody UserTableVo userTable){
        String username = userTable.getUserName();
        String uid = userTable.getUId();
        String password = userTable.getPassword();
        String orgPassword = userTable.getOrgPwd();
        QueryWrapper<UserTable> wrapper=new QueryWrapper<>();
        if(StringUtils.isNotEmpty(uid)){
            wrapper.eq("u_id", uid);
        }
        if(StringUtils.isNotEmpty(username)){
            wrapper.eq("user_name", username);
        }
        UserTable userTable1 = userTabService.getOne(wrapper);
        if(StringUtils.isNotEmpty(orgPassword)){
            if(!new Md5Hash(orgPassword, username, 3).toString().equals(userTable1.getPassword())){
                return Lay.error().msg("请输入正确的原始密码");
            }
            password = new Md5Hash(password, username, 3).toString();
            userTable1.setPassword(password);
        } else {
            List<String> roleId = userTable.getRoleId();

            //先执行删除
            QueryWrapper<UserRole> q = new QueryWrapper<>();
            q.eq("user_id", userTable1.getUId());
            userRoleService.remove(q);
            for (int i = 0; i < roleId.size(); i++) {
                String s =  roleId.get(i);
                q = new QueryWrapper<>();
                q.eq("user_id", userTable1.getUId());
                q.eq("role_id", s);
//                if(userRoleService.count(q) == 0){
                UserRole userRoleRelationshipTable = new UserRole();
                userRoleRelationshipTable.setUserId(userTable1.getUId());
                userRoleRelationshipTable.setRoleId(s);
                userRoleService.save(userRoleRelationshipTable);
//                }

            }
        }
//        userTable1.setUserName(username);
        userTable1.setShowName(userTable.getShowName());
        userTable1.setLevel(userTable.getLevel());
        userTabService.updateById(userTable1);
        return Lay.ok().msg("修改成功");
    }

    @ApiOperation("查询账号条件")
    @RequestMapping(value = "selectUser",method = RequestMethod.POST,name = "API-USER")
    public Lay selectTree(@RequestBody UserQueryVo userQueryVo, HttpServletRequest request){
        Claims claims = JwtUtils.getMemberIdByJwtToken(request);

        userQueryVo.setCityId((String) claims.get("regional"));
        if(StringUtils.isEmpty(userQueryVo.getDisId())) {
            userQueryVo.setDisId((String) claims.get("did"));
        }
        if(StringUtils.isEmpty(userQueryVo.getTowId())) {
            userQueryVo.setTowId((String) claims.get("tid"));
        }
        if(StringUtils.isEmpty(userQueryVo.getResId())){
            userQueryVo.setResId((String) claims.get("rid"));
        }
        Long limit = userQueryVo.getLimit();
        Long page = userQueryVo.getPage();
        IPage<UserTableVo> getAllVoList = userTabService.selectAllList(page, limit, userQueryVo);
        long total = getAllVoList.getTotal();
        List<UserTableVo> records = getAllVoList.getRecords();
        List<UserTableVo> list=new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            UserTableVo userTableVo = records.get(i);
            List<String> roleid = this.userRoleService.selectIdsByUid(userTableVo.getUId());
            userTableVo.setRoleId(roleid);
            userTableVo.setRoleTable(roleid.stream().map(str -> roleService.findById(str)).collect(Collectors.toList()));
            list.add(userTableVo);
        }
        return Lay.ok().count(total).data(list);
    }

    @ApiOperation("按照角色查询用户")
    @GetMapping("/role/{id}")
    public Lay findByRoleId(@PathVariable String id){
        return Lay.ok().data(this.userTabService.findByRoleId(id));
    }
}
