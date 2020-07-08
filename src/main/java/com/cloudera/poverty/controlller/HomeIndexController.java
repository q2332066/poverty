//package com.cloudera.poverty.controlller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.cloudera.poverty.common.result.Lay;
//import com.cloudera.poverty.common.result.R;
//import com.cloudera.poverty.entity.admin.UserTable;
//import com.cloudera.poverty.entity.vo.LoginVo;
//import com.cloudera.poverty.service.UserTabService;
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.crypto.SecureRandomNumberGenerator;
//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.apache.shiro.crypto.hash.SimpleHash;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.HtmlUtils;
//
//import java.io.Serializable;
//
//@RestController
//@RequestMapping("/min/est/user")
//@Api(description = "登录管理")
//@CrossOrigin //跨域
//@Slf4j
//public class HomeIndexController {
//    @Autowired
//    private UserTabService userTabService;
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public R login( LoginVo loginVo) {
//        String tokenuser=userTabService.login(loginVo);
//        String password = loginVo.getPassword();
//        String username = loginVo.getUsername();
//        // 从SecurityUtils里边创建一个 subject
//        Subject subject = SecurityUtils.getSubject();
//        //md5加密 密码,盐,加密次数
//            password = new Md5Hash(password, username, 3).toString();
//        String id = (String) subject.getSession().getId();
//        // 在认证提交前准备 token（令牌）
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        // 执行认证登陆
//        try {
//            subject.login(token);
//        } catch (UnknownAccountException uae) {
//            return R.ok().message("未知账户");
//        } catch (IncorrectCredentialsException ice) {
//            return R.ok().message("密码不正确");
//        } catch (LockedAccountException lae) {
//            return R.ok().message("账户已锁定");
//        } catch (ExcessiveAttemptsException eae) {
//            return R.ok().message("用户名或密码错误次数过多");
//        } catch (AuthenticationException ae) {
//            return R.ok().message("用户名或密码不正确");
//        }
//        if (subject.isAuthenticated()) {
//            Serializable id1 = subject.getSession().getId();
////            System.out.println("--------------------------------------------------------------------------");
////            QueryWrapper<UserTable> wrapper=new QueryWrapper<>();
////            wrapper.eq("user_name",username);
////            UserTable one = userTabService.getOne(wrapper);
////           return R.ok().message("登陆成功").data("sessionId",id1).data("userInfo",one);
//
//            return R.ok().message("登陆成功").data("sessionId",id1).data("token",tokenuser);
//        } else {
//            token.clear();
//            return R.ok().message("登陆失败");
//        }
//    }
//
//    @ResponseBody
//    @GetMapping("api/logout")
//    public Lay logout() {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        return Lay.ok().msg("成功登出");
//    }
//}
