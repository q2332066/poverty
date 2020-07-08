package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.R;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.common.utils.JwtInfo;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.admin.*;
import com.cloudera.poverty.entity.region.ResettlementPointTable;
import com.cloudera.poverty.entity.region.TownshipTable;
import com.cloudera.poverty.entity.vo.*;
import com.cloudera.poverty.mapper.*;
import com.cloudera.poverty.service.UserTabService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.cloudera.poverty.common.utils.MD5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserTabServiceImpl extends ServiceImpl<UserTabMapper, UserTable> implements UserTabService {

    @Autowired
    private DistrictTableMapper districtTableMapper;
    @Autowired
    private TownshipTableMapper townshipTableMapper;
    @Autowired
    private ResettlementPointTableMapper resettlementPointTableMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

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
                UserRoleRelationshipTable userRoleRelationshipTable = new UserRoleRelationshipTable();
                userRoleRelationshipTable.setUserId(user.getUId());
                userRoleRelationshipTable.setRoleId(s);
                userRoleMapper.insert(userRoleRelationshipTable);
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
        QueryWrapper<UserRoleRelationshipTable> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userTable.getUId());
        List<UserRoleRelationshipTable> userRoleRelationshipTables = userRoleMapper.selectList(wrapper);
        List<String> roleTablesList=new ArrayList<>();
        List<String> authorityTableList=new ArrayList<>();
        for (int i = 0; i < userRoleRelationshipTables.size(); i++) {
            String roleId = userRoleRelationshipTables.get(i).getRoleId();
            RoleTable roleTable = roleMapper.selectById(roleId);
            roleTablesList.add(roleTable.getRoleName());
            QueryWrapper<RolePermissionRelationshipTable> wrapperAuth=new QueryWrapper<>();
            wrapperAuth.eq("role_id",roleId);
            List<RolePermissionRelationshipTable> rolePermissionRelationshipTables = roleAuthorityMapper.selectList(wrapperAuth);
            for (int j = 0; j < rolePermissionRelationshipTables.size(); j++) {
                String authorityId = rolePermissionRelationshipTables.get(j).getAuthorityId();
                AuthorityTable authorityTable = authorityMapper.selectById(authorityId);
                if (null!=authorityTable){
                    if (null!=authorityTable.getCode()||authorityTable.getCode().equals("")){
                        authorityTableList.add(authorityTable.getCode());
                    }
                }
            }
        }
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setUId(userTable.getUId());
        jwtInfo.setShowName(userTable.getShowName());
        jwtInfo.setRegional(userTable.getRegionalId());
        jwtInfo.setLevel(userTable.getLevel());
        jwtInfo.setRoleTables(roleTablesList);
        jwtInfo.setAuthorityTables(authorityTableList);
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 7200);
        return jwtToken;
    }

    @Override
    public UserVo selectAll(String username) {
        UserVo userVo = new UserVo();
        QueryWrapper<UserTable> wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",username);
        UserTable userTable = baseMapper.selectOne(wrapper);
        BeanUtils.copyProperties(userTable,userVo);
        QueryWrapper<UserRoleRelationshipTable> wrapperRole=new QueryWrapper();
        wrapperRole.eq("user_id",userTable.getUId());
        List<UserRoleRelationshipTable> userRoleRelationshipTables = userRoleMapper.selectList(wrapperRole);

        List<RoleVo> roleVoList=new ArrayList<>();

        for (int i = 0; i < userRoleRelationshipTables.size(); i++) {
            UserRoleRelationshipTable userRoleRelationshipTable = userRoleRelationshipTables.get(i);
            RoleTable roleTable = roleMapper.selectById(userRoleRelationshipTable.getRoleId());
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(roleTable,roleVo);
            QueryWrapper<RolePermissionRelationshipTable> wrapperAuth=new QueryWrapper<>();
            wrapperAuth.eq("role_id",roleTable.getRId());
            List<RolePermissionRelationshipTable> rolePermissionRelationshipTables = roleAuthorityMapper.selectList(wrapperAuth);

            List<AuthorityTable> authList=new ArrayList<>();

            for (int j = 0; j < rolePermissionRelationshipTables.size(); j++) {
                RolePermissionRelationshipTable rolePermissionRelationshipTable = rolePermissionRelationshipTables.get(j);
                AuthorityTable authorityTable = authorityMapper.selectById(rolePermissionRelationshipTable.getId());
                authList.add(authorityTable);
            }
            roleVo.setAuList(authList);
            roleVoList.add(roleVo);
        }
        userVo.setRoleVoList(roleVoList);
        return userVo;
    }

    @Override
    public UserVo selectrole(String uId) {
        UserVo userVo = new UserVo();
        UserTable userTable = baseMapper.selectById(uId);
        BeanUtils.copyProperties(userTable,userVo);
        QueryWrapper<UserRoleRelationshipTable> wrapperRole=new QueryWrapper();
        wrapperRole.eq("user_id",userTable.getUId());
        List<UserRoleRelationshipTable> userRoleRelationshipTables = userRoleMapper.selectList(wrapperRole);
        List<RoleVo> roleVoList=new ArrayList<>();
        for (int i = 0; i < userRoleRelationshipTables.size(); i++) {
            String roleId = userRoleRelationshipTables.get(i).getRoleId();
            RoleTable roleTable = roleMapper.selectById(roleId);
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(roleTable,roleVo);

            List<AuthorityTable> authList=new ArrayList<>();
            QueryWrapper<RolePermissionRelationshipTable> wrapperAuth=new QueryWrapper<>();
            wrapperAuth.eq("role_id",roleTable.getRId());
            List<RolePermissionRelationshipTable> rolePermissionRelationshipTables = roleAuthorityMapper.selectList(wrapperAuth);
            for (int j = 0; j < rolePermissionRelationshipTables.size(); j++) {
                String authId = rolePermissionRelationshipTables.get(j).getAuthorityId();
                AuthorityTable authorityTable = authorityMapper.selectById(authId);
                authList.add(authorityTable);
            }
            roleVo.setAuList(authList);
            roleVoList.add(roleVo);
        }
        userVo.setRoleVoList(roleVoList);
        return userVo;
    }

    @Override
    public IPage<UserTableVo> selectAllList(Long page, Long limit, UserQueryVo userQueryVo) {
        QueryWrapper<UserTableVo> wrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(userQueryVo.getResId())){
            System.out.println(userQueryVo.getResId()+"***************************************************");
            wrapper.eq("P.regional_id",userQueryVo.getResId());
            if (!StringUtils.isEmpty(userQueryVo.getShowName())){
                wrapper.eq("P.show_name",userQueryVo.getShowName());
            }
            if (!StringUtils.isEmpty(userQueryVo.getUserName())){
                wrapper.eq("P.user_name",userQueryVo.getUserName());
            }
            Page<UserTableVo> pageParam=new Page<>(page,limit);
            List<UserTableVo> records=baseMapper.selectAllList(pageParam,wrapper);
            return pageParam.setRecords(records);
        }

        if (!StringUtils.isEmpty(userQueryVo.getTowId())){
            String towId = userQueryVo.getTowId();
            QueryWrapper<ResettlementPointTable> wrapperTow=new QueryWrapper<>();
            wrapperTow.eq("township_id",userQueryVo.getTowId());
            List<ResettlementPointTable> resettlementPointTables = resettlementPointTableMapper.selectList(wrapperTow);
            for (int i = 0; i < resettlementPointTables.size(); i++) {
                String rId = resettlementPointTables.get(i).getRId();
                wrapper.eq("P.regional_id",rId).or();
            }
            wrapper.eq("P.regional_id",userQueryVo.getTowId());
            if (!StringUtils.isEmpty(userQueryVo.getShowName())){
                wrapper.eq("P.show_name",userQueryVo.getShowName());
            }
            if (!StringUtils.isEmpty(userQueryVo.getUserName())){
                wrapper.eq("P.user_name",userQueryVo.getUserName());
            }
            Page<UserTableVo> pageParam=new Page<>(page,limit);
            List<UserTableVo> records=baseMapper.selectAllList(pageParam,wrapper);
            return pageParam.setRecords(records);
        }



        if (!StringUtils.isEmpty(userQueryVo.getDisId())){
            QueryWrapper<TownshipTable> wrapperDis=new QueryWrapper<>();
            wrapperDis.eq("district_id",userQueryVo.getDisId());
            List<TownshipTable> townshipTables = townshipTableMapper.selectList(wrapperDis);
            for (int i = 0; i < townshipTables.size(); i++) {
                String tId = townshipTables.get(i).getTId();
                wrapper.eq("P.regional_id",tId).or();
                QueryWrapper<ResettlementPointTable> wrapperRes=new QueryWrapper<>();
                wrapperRes.eq("township_id",tId);
                List<ResettlementPointTable> resettlementPointTables = resettlementPointTableMapper.selectList(wrapperRes);
                for (int j = 0; j < resettlementPointTables.size(); j++) {
                    String rId = resettlementPointTables.get(j).getRId();
                    wrapper.eq("P.regional_id",rId).or();
                }
            }
            wrapper.eq("P.regional_id",userQueryVo.getDisId());
            if (!StringUtils.isEmpty(userQueryVo.getShowName())){
                wrapper.eq("P.show_name",userQueryVo.getShowName());
            }
            if (!StringUtils.isEmpty(userQueryVo.getUserName())){
                wrapper.eq("P.user_name",userQueryVo.getUserName());
            }
            Page<UserTableVo> pageParam=new Page<>(page,limit);
            List<UserTableVo> records=baseMapper.selectAllList(pageParam,wrapper);
            return pageParam.setRecords(records);
        }


        if (!StringUtils.isEmpty(userQueryVo.getCityId())){
            if (!StringUtils.isEmpty(userQueryVo.getShowName())){
                wrapper.eq("P.show_name",userQueryVo.getShowName());
            }
            if (!StringUtils.isEmpty(userQueryVo.getUserName())){
                wrapper.eq("P.user_name",userQueryVo.getUserName());
            }
            Page<UserTableVo> pageParam=new Page<>(page,limit);
            List<UserTableVo> records=baseMapper.selectAllList(pageParam,wrapper);
            return pageParam.setRecords(records);
        }
        return null;
    }
}
