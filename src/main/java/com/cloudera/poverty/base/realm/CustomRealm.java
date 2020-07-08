package com.cloudera.poverty.base.realm;

import com.cloudera.poverty.common.result.UserRE;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import com.cloudera.poverty.entity.vo.RoleVo;
import com.cloudera.poverty.entity.vo.UserVo;
import com.cloudera.poverty.service.UserTabService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义realm域
 */
public class CustomRealm extends AuthorizingRealm {
    public void setName(String name){
        super.setName("customRealm");
    }
    @Autowired
    private UserTabService userTabService;
    /**
     * 授权:根据安全数据获取用户具有权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取已认证用户数据
        UserVo user = (UserVo) principalCollection.getPrimaryPrincipal();//得到安全数据
        //根据用户认证信息获取权限信息(所有角色,所有权限)
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Set<String> roles=new HashSet<>();
        Set<String> authorityTableList=new HashSet<>();
        List<RoleVo> roleVoList = user.getRoleVoList();
        for (int i = 0; i < roleVoList.size(); i++) {
            RoleVo roleVo = roleVoList.get(i);
            roles.add(roleVo.getRoleName());
            List<AuthorityTable> auList = roleVo.getAuList();
            for (int j = 0; j < auList.size(); j++) {
                AuthorityTable authorityTable = auList.get(j);
                authorityTableList.add(authorityTable.getCode());
            }
        }
        info.setRoles(roles);
        info.setStringPermissions(authorityTableList);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException 传递到用户名密码
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户名,密码
        UsernamePasswordToken upToken=(UsernamePasswordToken)authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        //根据用户名查询用户
        UserVo user = userTabService.selectrole(username);
        if (user.getIsDeleted()){
            return null;
        }
        UserRE.level=user.getLevel();
        UserRE.user =user.getRegionalId();
        //判断用户是否存在密码是否一致
        if (user!=null&&user.getPassword().equals(password)){
            //如果一致返回安全数据,构造方法,安全数据,密码,realm域名
            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
            return info;
        }
        //不一致返回null抛出异常
        return null;
    }
}
