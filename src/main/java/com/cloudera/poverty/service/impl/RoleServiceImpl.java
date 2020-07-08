package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.admin.RolePermissionRelationshipTable;
import com.cloudera.poverty.entity.admin.RoleTable;
import com.cloudera.poverty.entity.vo.RoleVo;
import com.cloudera.poverty.mapper.RoleAuthorityMapper;
import com.cloudera.poverty.mapper.RoleMapper;
import com.cloudera.poverty.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl  extends ServiceImpl<RoleMapper, RoleTable> implements RoleService {

    /**
     * 添加角色
     * @param roleVo
     * @return
     */

}
