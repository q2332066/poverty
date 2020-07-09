package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.admin.UserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fengtoos
 * @since 2020-04-08
 */
public interface IUserRoleService extends IService<UserRole> {

    List<String> selectIdsByUid(String id);
}
