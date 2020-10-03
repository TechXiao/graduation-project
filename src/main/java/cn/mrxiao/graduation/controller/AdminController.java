package cn.mrxiao.graduation.controller;

import cn.mrxiao.graduation.beans.Store;
import cn.mrxiao.graduation.service.StoreLoginService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mrxiao
 * @date 2020/5/4 - 21:22
 **/
@Controller
@CrossOrigin
public class AdminController {
    @Autowired
    Logger logger;
    @Autowired
    StoreLoginService storeLoginService;
    @Autowired
    SimpleMailMessage message;
    @Autowired
    JavaMailSenderImpl mailSender;

    @GetMapping("/admin")
    public String admin(HttpServletRequest request){
        String loginEmail="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail=c.getValue();
                if(c.getName().equals("up"));
            }
        }
        request.getSession().removeAttribute(loginEmail+"AdminLoginJudge");
        return "admin/login";
    }

    @PostMapping("/adminLogin")
    public String adminLogin(@RequestParam("user")String user, @RequestParam("pwd")String pwd, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){
        if(user.equals("jinbing.xiao@foxmail.com")&&pwd.equals("12345600")){
            logger.info(user+"管理员登录成功");
            Cookie cookie=new Cookie("loginEmail",user);
            response.addCookie(cookie);
            cookie.setMaxAge(2*60*60);
            request.getSession().setAttribute(user+"AdminLoginJudge",true);
            request.getSession().setMaxInactiveInterval(2*60*60);
            return "redirect:/admin/admin";
        }
        attributes.addFlashAttribute("msg","账号或密码错误！后台管理系统只允许管理员登录！");
        return "redirect:/admin";
    }

    @GetMapping("/admin/admin")
    public String adminHome(HttpServletRequest request,RedirectAttributes attributes){
        String loginEmail="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail")){
                    loginEmail=c.getValue();
                    break;
                }
            }
        }
        if(request.getSession().getAttribute(loginEmail+"AdminLoginJudge")==null){
            attributes.addFlashAttribute("msg","账号或密码错误！后台管理系统只允许开发者登录！");
            return "redirect:/admin";
        }
        return "admin/admin";
    }

    @ResponseBody
    @GetMapping("/getAllStore")
    public List<Store> getAllStore(HttpServletRequest request){
        String loginEmail="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail")){
                    loginEmail=c.getValue();
                    break;
                }
            }
        }
        if(request.getSession().getAttribute(loginEmail+"AdminLoginJudge")==null){
           return null;
        }
        List<Store> stores=storeLoginService.getAllStore();
        return stores;
    }
    @ResponseBody
    @GetMapping("/changeStoreLicense/{storeId}/{license}")
    public Integer changeStoreLicense(@PathVariable("storeId")Integer storeId,@PathVariable("license")Integer license,HttpServletRequest request){
        String loginEmail="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail")){
                    loginEmail=c.getValue();
                    break;
                }
            }
        }
        if(request.getSession().getAttribute(loginEmail+"AdminLoginJudge")==null){
            return 0;
        }
        Integer result=storeLoginService.changeStoreLicenseByStoreId(storeId,license);
        storeLoginService.updateAllStore();
        Store store=storeLoginService.getById(storeId);
        storeLoginService.updateCityStoreList(store.getCity());
        storeLoginService.updateStoreById(storeId);
        if(license==1){
            message.setSubject("餐厅审核通过");
            message.setText("您的餐厅“"+store.getStoreName()+"”已通过本平台的审核。");
        }else{
            message.setSubject("停止展示通知");
            message.setText("您的餐厅“"+store.getStoreName()+"”因某种原因被取消展示许可，再次审核通过之前将不再本平台展示。请检察原因，以备管理员审核。如有问题请发邮件至jinbing.xiao@foxmail.com咨询");
        }
        message.setTo(store.getEmail());
        message.setFrom("Food在线<jinbing.xiao@foxmail.com>");
        mailSender.send(message);
        return result;
    }
}
