package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.admin.RolePermissionRelationshipTable;
import com.cloudera.poverty.entity.vo.AuthVo;

import java.util.List;

public interface RoleAuthorityService extends IService<RolePermissionRelationshipTable> {
    List<AuthVo> selectList(String roleId);
}
