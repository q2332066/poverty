package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.vo.AuthVo;

import java.util.List;

public interface AuthorityService extends IService<AuthorityTable> {
    List<AuthVo> getList();

    void updateAuthorityByRoleId(String roleId, List<String> authIds);

    List<AuthVo> getAuthorityByid(String roleId);
}
