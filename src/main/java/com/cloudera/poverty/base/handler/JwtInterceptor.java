package com.cloudera.poverty.base.handler;

import com.cloudera.poverty.base.exception.PaException;
import com.cloudera.poverty.common.result.ResultCodeEnum;
import com.cloudera.poverty.common.result.UserRE;
import com.cloudera.poverty.common.utils.ExceptionUtils;
import com.cloudera.poverty.common.utils.JwtInfo;
import com.cloudera.poverty.common.utils.JwtUtils;
import com.cloudera.poverty.entity.admin.AuthorityTable;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //重写preHandle方法，在请求发生前执行。
        // 1.通过request获取请求token信息
        String jwtToken = request.getHeader("token");
        if (!StringUtils.isEmpty(jwtToken)){
            Claims claims=JwtUtils.getMemberIdByJwtToken(request);
            UserRE.level= (String) claims.get("level");
            UserRE.user= (String) claims.get("regional");
            List<String> auth= (List<String>) claims.get("auths");
            HandlerMethod h= (HandlerMethod) handler;
            RequestMapping annotation = h.getMethodAnnotation(RequestMapping.class);
            String name = annotation.name();
            for (int i = 0; i < auth.size(); i++) {
                String s = auth.get(i);
                if (s.equals(name)){
                    request.setAttribute("user_claims",claims);
                    System.out.println(i+"+++++++++++++++++++++++++++"+auth.size());
                    return true;

                }
                if (i==auth.size()){
                    throw new PaException(ResultCodeEnum.LOGIN_ACL);
                }
            }
        }else{
            throw new PaException(ResultCodeEnum.LOGIN_AUTH);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //重写postHandle方法，在请求完成后执行。
        super.postHandle(request, response, handler, modelAndView);
    }

}
