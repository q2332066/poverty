package com.cloudera.poverty.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudera.poverty.entity.admin.Authorization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-29
 */
public interface AuthorizationMapper extends BaseMapper<Authorization> {

    /**
     * 根据用户ID获取权限项
     * @param userId
     * @return
     */
    List<Authorization> selectByUserId(@Param("userId") String userId);

    /**
     * 查找树结构
     * @return
     */
    List<Authorization> selectTree();

    /**
     * 根据父ID获取树
     * @param parent
     * @return
     */
    List<Authorization> selectTreeByParent(String parent);

    /**
     * 递归查询所有树
     * @return
     */
    List<Authorization> selectAllTree();

    /**
     * 递归根据父ID查询树
     * @return
     */
    List<Authorization> selectTreeByParentAll(String parent);

    /**
     * 根据角色ID获取权限
     * @param roleId
     * @return
     */
    List<Authorization> findByRoleId(String roleId);

}
