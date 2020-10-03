package cn.mrxiao.graduation.config;

import cn.mrxiao.graduation.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mrxiao
 * @date 2020/4/24 - 23:06
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean loginJudge=false;
        String loginEmail="",up="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail=c.getValue();
                if(c.getName().equals("up"))
                    up=c.getValue();
            }
        }
        if(!loginJudge&&(request.getSession().getAttribute(loginEmail + "LoginJudge"))!=null)
            loginJudge=(Boolean) request.getSession().getAttribute(loginEmail + "LoginJudge");
        if(loginEmail.equals("jinbing.xiao@foxmail.com")&&request.getSession().getAttribute(loginEmail+"AdminLoginJudge")!=null)
            loginJudge=(Boolean)request.getSession().getAttribute(loginEmail+"AdminLoginJudge");
        if(!loginJudge){
            request.setAttribute("msg","请先登录，商家请点击右上角红色按钮跳转商家登录页面");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }
        return true;
    }
}
