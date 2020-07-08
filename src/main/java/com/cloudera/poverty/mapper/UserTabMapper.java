package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudera.poverty.entity.admin.RoleTable;
import com.cloudera.poverty.entity.admin.UserTable;
import com.cloudera.poverty.entity.vo.UserTableVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTabMapper extends BaseMapper<UserTable> {
    void deleteUser(
            @Param(Constants.WRAPPER) QueryWrapper<UserTable> wrapper);

    List<UserTableVo> selectDist();

    List<UserTableVo> selectTow();

    List<UserTableVo> selectRes();

    List<UserTableVo> select();

    List<UserTableVo> selectAllList(Page<UserTableVo> pageParam,
                                    @Param(Constants.WRAPPER)QueryWrapper<UserTableVo> wrapper);
}
