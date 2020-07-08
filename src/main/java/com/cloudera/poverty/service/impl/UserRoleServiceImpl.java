package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.entity.admin.RoleTable;
import com.cloudera.poverty.entity.admin.UserRoleRelationshipTable;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.mapper.RoleMapper;
import com.cloudera.poverty.mapper.UserRoleMapper;
import com.cloudera.poverty.mapper.UserTabMapper;
import com.cloudera.poverty.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleRelationshipTable> implements UserRoleService {


    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserTabMapper userTabMapper;

    /**
     * 账号角色添加
     * @param uRList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveList(List<UserRoleRelationshipTable> uRList,String level) {

        if (null!=uRList||uRList.size()!=0){
            for (int i = 0; i < uRList.size(); i++) {

                UserRoleRelationshipTable userRoleRelationshipTable = uRList.get(i);
                String userId = userRoleRelationshipTable.getUserId();
                UserTable userTable = userTabMapper.selectById(userId);
                int i1 = Integer.parseInt(userTable.getLevel());
                int i2 = Integer.parseInt(level);
                if (i2-i1==1){
                    baseMapper.insert(userRoleRelationshipTable);
                }else {
                    throw new PaException(ResultCodeEnum.LOGIN_ACL);
                }

            }
            return false;
        }
            return true;
    }

    /**
     * 查询账号拥有角色
     * @param uid
     * @return
     */
    @Override
    public List<RoleTable> selectRole(String uid) {
        QueryWrapper<UserRoleRelationshipTable> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",uid);
        List<UserRoleRelationshipTable> userRoleRelationshipTables = baseMapper.selectList(wrapper);
        if (null==userRoleRelationshipTables||userRoleRelationshipTables.size()==0){
            return null;
        }
        List<RoleTable> list=new ArrayList<>();
        for (int i = 0; i < userRoleRelationshipTables.size(); i++) {
            String roleId = userRoleRelationshipTables.get(i).getRoleId();
            RoleTable roleTable = roleMapper.selectById(roleId);
            list.add(roleTable);
        }
        return list;
    }
}
