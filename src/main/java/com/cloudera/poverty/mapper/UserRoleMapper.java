package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudera.poverty.entity.admin.UserRoleRelationshipTable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper  extends BaseMapper<UserRoleRelationshipTable> {
}
