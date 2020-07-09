package com.cloudera.poverty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudera.poverty.entity.admin.Authorization;
import com.cloudera.poverty.mapper.AuthorizationMapper;
import com.cloudera.poverty.service.IAuthorizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengtoos
 * @since 2019-06-29
 */
@Service
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationMapper, Authorization> implements IAuthorizationService {

    @Autowired
    AuthorizationMapper authorizationMapper;

    @Override
    public List<Authorization> selectByUserId(String id) {
        if(id == null){
            return Collections.emptyList();
        }
        return this.authorizationMapper.selectByUserId(id);
    }

    @Override
    public List<Authorization> findTree(String pid) {
        if("all".equals(pid)){
            return this.authorizationMapper.selectAllTree();
        }

        if (StringUtils.isEmpty(pid)){
            return this.authorizationMapper.selectTree();
        } else {
            return this.authorizationMapper.selectTreeByParent(pid);
        }
    }

    @Override
    public List<Authorization> getMenus(String userid) {
        List<Authorization> auths = this.selectByUserId(userid);
        List<Authorization> rs = this.getBaseMapper().selectAllTree();
        for(Authorization item : auths){
            setMenu(item.getPath(), rs);
        }
        return rs;
    }

    /**
     * 递归设置菜单
     * @param code
     * @param list
     */
    private void setMenu(String code, List<Authorization> list){
        for(Authorization item : list){
            if(!item.isMenu()){
                item.setMenu(code.contains(item.getId())&&item.getStatus()!=2);
            }
            setMenu(code, item.getChildren());
        }
    }

    @Override
    public boolean saveOrUpdate(Authorization entity) {
        String parentId = entity.getParent();
        if(StringUtils.isEmpty(parentId)){
            entity.setParent(null);
        }

        if(1 == entity.getStatus()){
            entity.setIsLeaf(true);
        } else {
            entity.setIsLeaf(false);
        }

        if(StringUtils.isEmpty(entity.getIcon())){
            entity.setIcon("layui-icon-form");
        }

        super.saveOrUpdate(entity);
        if(StringUtils.isNotEmpty(parentId)){
            Authorization parent = this.getById(parentId);
            entity.setPath(String.format("%s%s,", parent.getPath(), entity.getId()));
            entity.setLevel(parent.getLevel() + 1);
        } else {
            entity.setPath(String.format("%s,", entity.getId()));
            entity.setLevel(1);
        }
        return this.updateById(entity);
    }
}
