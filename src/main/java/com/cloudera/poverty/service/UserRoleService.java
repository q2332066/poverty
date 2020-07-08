package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.admin.RoleTable;
import com.cloudera.poverty.entity.admin.UserRoleRelationshipTable;

import java.util.List;

public interface UserRoleService extends IService<UserRoleRelationshipTable> {

    Boolean saveList(List<UserRoleRelationshipTable> uRList, String level);

    List<RoleTable> selectRole(String uid);
}
