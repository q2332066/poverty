package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.entity.vo.*;

import java.util.List;

public interface UserTabService  extends IService<UserTable> {
    String saveUser(UserTableVo userTable);

    Boolean removeUser(String uid);

    List<UserTableVo> selectList();

    String login(LoginVo loginVo);

    UserVo selectAll(String uid);

    UserVo selectrole(String username);

    IPage<UserTableVo> selectAllList(Long page, Long limit, UserQueryVo userQueryVo);

}
