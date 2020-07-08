package com.cloudera.poverty.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义的sessionManager
 */
public class CustomSessionManager extends DefaultWebSessionManager {
    /**
     * 请求头中具有sessionId
     *         请求头:Authorization:sessionId
     * 指定session获取方式
     */
    protected Serializable getSessionId(ServletRequest request, ServletResponse response){
        //获取请求头数据
        String id= WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(id)){
            //如果没有携带,生成新的sessionId
            return super.getSessionId(request,response);

        }else{
            //返回sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        }
    }
}
