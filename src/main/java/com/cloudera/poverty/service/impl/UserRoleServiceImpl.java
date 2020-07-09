package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.admin.UserRole;
import com.cloudera.poverty.mapper.UserRoleMapper;
import com.cloudera.poverty.service.IUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-08
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    public List<String> selectIdsByUid(String id) {
        QueryWrapper<UserRole> query = new QueryWrapper<>();
        query.eq("user_id", id);
        List<UserRole> list = this.getBaseMapper().selectList(query);
        return list.stream().map(i -> i.getRoleId()).collect(Collectors.toList());
    }
}
