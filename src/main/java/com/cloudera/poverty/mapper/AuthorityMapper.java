package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.admin.RolePermissionRelationshipTable;
import com.cloudera.poverty.entity.vo.AuthVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityMapper extends BaseMapper<AuthorityTable> {
    List<AuthVo> selectAuth(String parentId);

    List<AuthVo> selectAuthWapper(
            @Param(Constants.WRAPPER) QueryWrapper<AuthVo> wrapper);

    List<AuthVo> selectByRoleId(@Param("roleId") String roleId);

    void deleteRolePermisionByRoleId(@Param("roleid") String roleId);

    void insertArolePeri(@Param("roleId") String roleId, @Param("authId") String authId);
}

