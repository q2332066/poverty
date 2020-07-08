package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudera.poverty.entity.admin.RolePermissionRelationshipTable;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAuthorityMapper extends BaseMapper<RolePermissionRelationshipTable> {
}
