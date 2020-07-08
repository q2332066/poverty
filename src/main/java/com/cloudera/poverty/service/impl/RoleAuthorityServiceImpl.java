package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.admin.RolePermissionRelationshipTable;
import com.cloudera.poverty.entity.vo.AuthVo;
import com.cloudera.poverty.mapper.AuthorityMapper;
import com.cloudera.poverty.mapper.RoleAuthorityMapper;
import com.cloudera.poverty.service.RoleAuthorityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RolePermissionRelationshipTable> implements RoleAuthorityService {
    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    @Override
    public List<AuthVo> selectList(String roleId) {

        QueryWrapper<RolePermissionRelationshipTable> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        List<RolePermissionRelationshipTable> rolePermissionRelationshipTables = baseMapper.selectList(wrapper);

        List<AuthVo> list=new ArrayList<>();
        for (int i = 0; i < rolePermissionRelationshipTables.size(); i++) {
            String authorityId = rolePermissionRelationshipTables.get(i).getAuthorityId();

            QueryWrapper<AuthorityTable> wrapperAuth=new QueryWrapper<>();
            wrapperAuth.eq("authority_id",authorityId);
            wrapperAuth.eq("authority_father_id","0");
            AuthorityTable authorityTable = authorityMapper.selectOne(wrapperAuth);
            if (null!=authorityTable){
                List<AuthVo> listtwo=new ArrayList<>();
                for (int j = 0; j < rolePermissionRelationshipTables.size(); j++) {
                    String twoAuthorityId = rolePermissionRelationshipTables.get(j).getAuthorityId();
                    QueryWrapper<AuthorityTable> wrapperAuthTwo=new QueryWrapper<>();
                    wrapperAuthTwo.eq("authority_id",twoAuthorityId);
                    wrapperAuthTwo.eq("authority_father_id",authorityTable.getAuthorityId());
                    AuthorityTable authorityTableTwo = authorityMapper.selectOne(wrapperAuthTwo);
                    if (null==authorityTableTwo){
                        continue;
                    }
                    AuthVo authVotwo = new AuthVo();
                    BeanUtils.copyProperties(authorityTableTwo,authVotwo);
                    listtwo.add(authVotwo);
                }
                AuthVo authVo = new AuthVo();
                BeanUtils.copyProperties(authorityTable,authVo);
                authVo.setSubMenus(listtwo);
                list.add(authVo);
            }
        }
        //List<AuthVo> list=authorityMapper.selectAuthWapper(wrapperAuth);
        return list;
    }
}
