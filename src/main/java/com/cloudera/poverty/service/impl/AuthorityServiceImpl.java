package com.cloudera.poverty.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.vo.AuthVo;
import com.cloudera.poverty.mapper.AuthorityMapper;
import com.cloudera.poverty.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, AuthorityTable> implements AuthorityService {

    @Override
    public List<AuthVo> getList() {
        List<AuthVo> authVos = baseMapper.selectAuth("0");
        return authVos;
    }



    @Override
    public void updateAuthorityByRoleId(String roleId, List<String> authIds) {
        baseMapper.deleteRolePermisionByRoleId(roleId);
        for (String authId : authIds) {
            baseMapper.insertArolePeri(roleId,authId);
        }
    }

    @Override
    public List<AuthVo> getAuthorityByid(String roleId) {
        return baseMapper.selectByRoleId(roleId);
    }

}
