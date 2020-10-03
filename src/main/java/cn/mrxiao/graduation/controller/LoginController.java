package cn.mrxiao.graduation.controller;

import cn.mrxiao.graduation.beans.Customer;
import cn.mrxiao.graduation.service.LoginService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author mrxiao
 * @date 2020/4/14 - 19:25
 **/
@Controller
@CrossOrigin
public class LoginController {
    //日志记录器
    @Autowired
    Logger logger;
    @Autowired
    LoginService loginService;

    //首页映射
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String loginEmail="",up="";
        Cookie cookie = new Cookie("loginEmail","");
        cookie.setMaxAge(0);
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail=c.getValue();
                if(c.getName().equals("up"))
                    up=c.getValue();
            }
        }
        //向浏览器返回名为loginEmail的cookie，存活时间为0，故意味着删除该cookie
        response.addCookie(cookie);
        request.getSession().removeAttribute(loginEmail+"LoginJudge");
        return "login/login";

    }

    /**
     * 获取验证码映射
     * @param email 邮箱
     * @return
     */
    @ResponseBody
    @PostMapping("/produceSecurityCode")
    public String produceSecurityCode(@RequestParam(value = "email")String email,HttpServletResponse response, HttpServletRequest request){
        String security=null;
        try {
            security=loginService.sendSecurityCode(email);
            logger.info("顾客获取验证码成功"+email);
        }catch (Exception e){
            logger.error("顾客获取验证码失败"+email);
            return "false";
        }

        request.getSession().setAttribute(email+"SecurityCode",security);
        //设置验证码session存活时间为15分钟，秒为单位，即在没有活动15分钟后，session将失效
        request.getSession().setMaxInactiveInterval(15*60);
        return "true";
    }

    /**
     *  提交登录映射
     * @param email 邮箱
     * @param securityCode 验证码
     * @return
     */
    @PostMapping("/submit-login")
    public String submitLogin(@RequestParam("email")String email, @RequestParam("securityCode")String securityCode, RedirectAttributes attributes
            , HttpServletRequest request, HttpServletResponse response,HttpSession session){
        email+="@qq.com";
        if ((request.getSession().getAttribute(email + "SecurityCode"))!=null
                &&securityCode.equals(request.getSession().getAttribute(email + "SecurityCode"))){
            logger.info("顾客登陆成功"+email);
            request.getSession().setAttribute(email+"LoginJudge",true);
            request.getSession().setMaxInactiveInterval(14*24*60*60);
            Cookie cookie=new Cookie("loginEmail",email);
            cookie.setMaxAge(14*24*60*60);
            response.addCookie(cookie);
//            String uuid=UUID.randomUUID().toString().replace("-", "");
            //cookie=new Cookie("up", uuid);
            //cookie.setMaxAge(14*24*60*60);
            //response.addCookie(cookie);
            if(loginService.checkCustomerExist(email)){
                return "redirect:/";
            }
            return "redirect:/login/register-info";
        }else{
            //重定向到映射需要用RedirectAttributes传递信息
            attributes.addFlashAttribute("msg","验证码错误，请重新获取");
            logger.error("顾客登陆失败，验证码错误"+email);
            return "redirect:/login";
        }
    }

    /**
     * 注册信息登记页面映射
     * @return
     */
    @GetMapping("/login/register-info")
    public String registerInfo(HttpServletRequest request, Model model){
        String loginEmail="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail=c.getValue();
            }
        }
        Customer customer=loginService.getByEmail(loginEmail);
        if (customer!=null){
            model.addAttribute("loginUserName",customer.getUserName());
            model.addAttribute("loginUserPhoneNumber",customer.getPhoneNumber());
        }
        return "login/register-info";
    }

    /**
     * @return
     */
    @PostMapping("/submit-info")
    public String submitRegisterInfo(Customer customer, HttpServletRequest request, HttpServletResponse response){
        String loginEmail="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail=c.getValue();
            }
        }
        Customer customer1=loginService.getByEmail(loginEmail);
        if(customer1!=null){
            customer1.setUserName(customer.getUserName());
            customer1.setPhoneNumber(customer.getPhoneNumber());
            loginService.updateInfo(customer1);
        }else {
            loginService.updateInfo(customer);
        }
        return "redirect:/";
    }
}
