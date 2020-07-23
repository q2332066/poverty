package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.common.utils.JwtInfo;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.admin.UserRole;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.entity.region.DistrictTable;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.region.TownshipTable;
import com.cloudera.poverty.entity.vo.LoginVo;
import com.cloudera.poverty.entity.vo.UserQueryVo;
import com.cloudera.poverty.entity.vo.UserTableVo;
import com.cloudera.poverty.entity.vo.UserVo;
import com.cloudera.poverty.mapper.DistrictTableMapper;
import com.cloudera.poverty.mapper.ResettlementPointTableMapper;
import com.cloudera.poverty.mapper.TownshipTableMapper;
import com.cloudera.poverty.mapper.UserTabMapper;
import com.cloudera.poverty.service.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserTabServiceImpl extends ServiceImpl<UserTabMapper, UserTable> implements UserTabService {

    @Autowired
    private DistrictTableMapper districtTableMapper;
    @Autowired
    private TownshipTableMapper townshipTableMapper;
    @Autowired
    private ResettlementPointTableMapper resettlementPointTableMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    IUserRoleService userRoleService;

    @Override
    public String saveUser(UserTableVo userTable) {

        String username = userTable.getUserName();
        //是否被注册
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count<1){
            UserTable user = new UserTable();
            BeanUtils.copyProperties(userTable,user);
            user.setIsDeleted(false);
            String password = user.getPassword();
            password = new Md5Hash(password, username, 3).toString();
            Date dateTime = new Date();
            user.setCreateTime(dateTime);
            int insert = baseMapper.insert(user);
            List<String> roleId = userTable.getRoleId();

            for (int i = 0; i < roleId.size(); i++) {
                String s =  roleId.get(i);
                UserRole userRoleRelationshipTable = new UserRole();
                userRoleRelationshipTable.setUserId(user.getUId());
                userRoleRelationshipTable.setRoleId(s);
                userRoleService.save(userRoleRelationshipTable);
            }
        }else{
            return "用户已被注册";
        }
        return "注册成功";
    }

    @Override
    public Boolean removeUser(String uid) {
        UserTable userTable = baseMapper.selectById(uid);
        if (userTable.getIsDeleted()){
            userTable.setIsDeleted(false);
            baseMapper.updateById(userTable);
            return true;
        }else{
            userTable.setIsDeleted(true);
            baseMapper.updateById(userTable);
            return false;
        }
    }
    @Override
    public List<UserTableVo> selectList() {
        QueryWrapper<UserTableVo> wrapper1=new QueryWrapper<>();
        List<UserTableVo> userList1 = baseMapper.select();
        QueryWrapper<UserTable> wrapper3=new QueryWrapper<>();
        List<UserTableVo> userList3 = baseMapper.selectDist();
        QueryWrapper<UserTable> wrapper4=new QueryWrapper<>();
        List<UserTableVo> userList4 = baseMapper.selectTow();
        QueryWrapper<UserTable> wrapper5=new QueryWrapper<>();
        List<UserTableVo> userList5 = baseMapper.selectRes();
        userList1.addAll(userList3);
        userList1.addAll(userList4);
        userList1.addAll(userList5);
        return userList1;
    }

    @Override
    public String login(LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            throw new PaException(ResultCodeEnum.PARAM_ERROR);
        }
        //校验账号
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        UserTable userTable = baseMapper.selectOne(queryWrapper);
        if(userTable == null){
            throw new PaException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }
       // 校验密码
        password = new Md5Hash(password, username, 3).toString();
        if(!password.equals(userTable.getPassword())){
            throw new PaException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        //检验用户是否被禁用
        if(userTable.getIsDeleted()){
            throw new PaException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setUId(userTable.getUId());
        jwtInfo.setShowName(userTable.getShowName());
        jwtInfo.setRegional(userTable.getRegionalId());
        jwtInfo.setLevel(userTable.getLevel());
        jwtInfo.setDid(userTable.getDid());
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(userTable.getDid())){
            DistrictTable d = this.districtTableMapper.selectById(userTable.getDid());
            jwtInfo.setDname(d==null?null:d.getDistrict());
        }
        jwtInfo.setTid(userTable.getTid());
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(userTable.getTid())){
            TownshipTable d = this.townshipTableMapper.selectById(userTable.getTid());
            jwtInfo.setTname(d==null?null:d.getTownship());
        }
        jwtInfo.setRid(userTable.getRid());
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(userTable.getRid())){
            ResettlementPointTable d = this.resettlementPointTableMapper.selectById(userTable.getRid());
            jwtInfo.setRname(d==null?null:d.getResettlementPoint());
        }
        jwtInfo.setUserName(userTable.getUserName());
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 14400);
        return jwtToken;
    }

    @Override
    public UserVo selectAll(String username) {
        UserVo userVo = new UserVo();
        QueryWrapper<UserTable> wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",username);
        UserTable userTable = baseMapper.selectOne(wrapper);
        BeanUtils.copyProperties(userTable,userVo);
        return userVo;
    }

    @Override
    public UserVo selectrole(String uId) {
        UserVo userVo = new UserVo();
        UserTable userTable = baseMapper.selectById(uId);
        BeanUtils.copyProperties(userTable,userVo);
        return userVo;
    }

    @Override
    public IPage<UserTableVo> selectAllList(Long page, Long limit, UserQueryVo userQueryVo) {
        QueryWrapper<UserTableVo> wrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(userQueryVo.getResId())){
            System.out.println(userQueryVo.getResId()+"***************************************************");
            wrapper.eq("P.regional_id",userQueryVo.getCityId());
            if (!StringUtils.isEmpty(userQueryVo.getShowName())){
                wrapper.eq("P.show_name",userQueryVo.getShowName());
            }
            if (!StringUtils.isEmpty(userQueryVo.getUserName())){
                wrapper.eq("P.user_name",userQueryVo.getUserName());
            }
            if(!StringUtils.isEmpty(userQueryVo.getDisId())){
                wrapper.eq("P.did",userQueryVo.getDisId());
            }
            if(!StringUtils.isEmpty(userQueryVo.getDisId())){
                wrapper.eq("P.tid",userQueryVo.getTowId());
            }
            if(!StringUtils.isEmpty(userQueryVo.getDisId())){
                wrapper.eq("P.rid",userQueryVo.getResId());
            }
            Page<UserTableVo> pageParam=new Page<>(page,limit);
            List<UserTableVo> records=baseMapper.selectAllList(pageParam,wrapper);
            return pageParam.setRecords(records);
        }

        if (!StringUtils.isEmpty(userQueryVo.getTowId())){
            wrapper.eq("P.regional_id",userQueryVo.getCityId());
            if (!StringUtils.isEmpty(userQueryVo.getShowName())){
                wrapper.eq("P.show_name",userQueryVo.getShowName());
            }
            if (!StringUtils.isEmpty(userQueryVo.getUserName())){
                wrapper.eq("P.user_name",userQueryVo.getUserName());
            }
            if(!StringUtils.isEmpty(userQueryVo.getDisId())){
                wrapper.eq("P.did",userQueryVo.getDisId());
            }
            if(!StringUtils.isEmpty(userQueryVo.getDisId())){
                wrapper.eq("P.tid",userQueryVo.getTowId());
            }
            Page<UserTableVo> pageParam=new Page<>(page,limit);
            List<UserTableVo> records=baseMapper.selectAllList(pageParam,wrapper);
            return pageParam.setRecords(records);
        }



        if (!StringUtils.isEmpty(userQueryVo.getDisId())){
            wrapper.eq("P.regional_id",userQueryVo.getCityId());
            if (!StringUtils.isEmpty(userQueryVo.getShowName())){
                wrapper.eq("P.show_name",userQueryVo.getShowName());
            }
            if (!StringUtils.isEmpty(userQueryVo.getUserName())){
                wrapper.eq("P.user_name",userQueryVo.getUserName());
            }
            if(!StringUtils.isEmpty(userQueryVo.getDisId())){
                wrapper.eq("P.did",userQueryVo.getDisId());
            }
            Page<UserTableVo> pageParam=new Page<>(page,limit);
            List<UserTableVo> records=baseMapper.selectAllList(pageParam,wrapper);
            return pageParam.setRecords(records);
        }


//        if (!StringUtils.isEmpty(userQueryVo.getCityId())){
            if (!StringUtils.isEmpty(userQueryVo.getShowName())){
                wrapper.eq("P.show_name",userQueryVo.getShowName());
            }
            if (!StringUtils.isEmpty(userQueryVo.getUserName())){
                wrapper.eq("P.user_name",userQueryVo.getUserName());
            }
            Page<UserTableVo> pageParam=new Page<>(page,limit);
            List<UserTableVo> records=baseMapper.selectAllList(pageParam,wrapper);
            return pageParam.setRecords(records);
//        }
//        return null;
    }

    @Override
    public List<UserTableVo> findByRoleId(String roleId) {
        return this.baseMapper.findByRoleId(roleId);
    }
}
