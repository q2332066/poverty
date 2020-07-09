package com.cloudera.poverty.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudera.poverty.entity.admin.Authorization;

import java.util.List;

/**
 * <p>
 *  权限服务类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-29
 */
public interface IAuthorizationService extends IService<Authorization> {

    /**
     * 根据用户ID获取权限项
     * @param id
     * @return
     */
    List<Authorization> selectByUserId(String id);

    /**
     * 根据父ID获取下一级树结构
     * @param pid
     * @return
     */
    List<Authorization> findTree(String pid);

    /**
     * 获取用户菜单
     */
    List<Authorization> getMenus(String userid);
}
