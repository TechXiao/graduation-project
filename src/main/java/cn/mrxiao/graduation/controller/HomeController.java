package cn.mrxiao.graduation.controller;

import cn.mrxiao.graduation.beans.Customer;
import cn.mrxiao.graduation.beans.Store;
import cn.mrxiao.graduation.service.LoginService;
import cn.mrxiao.graduation.service.StoreLoginService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * @author mrxiao
 * @date 2020/4/23 - 21:00
 **/
@Controller
@CrossOrigin
public class HomeController {
    //日志记录器
    @Autowired
    Logger logger;
    @Autowired
    LoginService loginService;
    @Autowired
    StoreLoginService storeLoginService;

    @GetMapping({"/", "/home"})
    public String home(HttpServletRequest request, Model model, HttpServletResponse response) {
        boolean loginJudge = false;
        String loginEmail = "",sessionId="";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail = c.getValue();
            }
        }

        Customer customer = loginService.getByEmail(loginEmail);
        if(customer==null){
                logger.info("顾客未注册信息直接访问首页"+loginEmail);
                return "redirect:/login/register-info";
            }else{
                if(customer.getAddress()==""){
                    model.addAttribute("loginUser", customer.getUserName());
                    model.addAttribute("loginUserAddress", "请先定位");
                }else{
                    model.addAttribute("loginUser", customer.getUserName());
                    model.addAttribute("loginUserAddress", customer.getAddress());
                    model.addAttribute("loginUserCity",customer.getCity());
                    model.addAttribute("loginUserEmail",customer.getEmail());
                }
        }
        return "home";
    }

    @GetMapping("/location")
    public String location(HttpServletRequest request, Model model) {
        boolean loginJudge = false;
        String loginEmail = "";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail = c.getValue();
            }
        }
        Customer customer = loginService.getByEmail(loginEmail);
        if(customer==null){
            logger.info("顾客未注册信息直接访问首页"+loginEmail);
            return "redirect:/login/register-info";
            }
            model.addAttribute("city",customer.getCity());
        model.addAttribute("loginUser", customer.getUserName());
        return "location";
    }

    @GetMapping("/submit-location")
    public String submitLocation(Customer customer, HttpServletRequest request) {
        boolean loginJudge = false;
        String loginEmail = "";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail = c.getValue();
            }
        }
        Customer customer1 = loginService.getByEmail(loginEmail);
        customer1.setPositionLat(customer.getPositionLat());
        customer1.setPositionLng(customer.getPositionLng());
        customer1.setCity(customer.getCity());
        customer1.setAddress(customer.getAddress());
        loginService.updateInfo(customer1);
        storeLoginService.updateCityStoreList(customer1.getCity());
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/getAllStoresByCity/{city}")
    public Collection<Store> getAllStoresByCity(@PathVariable("city") String city){
        Collection<Store> stores=storeLoginService.getAllStoresByCity(city);
        return stores;
    }

    @ResponseBody
    @GetMapping("/getLoginUserInfo/{email}")
    public Customer getLoginUserInfo(@PathVariable("email") String email){
        Customer customer=loginService.getByEmail(email);
        return customer;
    }

    @GetMapping("/enterStore/{id}")
    public String enterStore(@PathVariable("id") Integer id,HttpServletRequest request,Model model){
        String loginEmail = "";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail = c.getValue();
            }
        }
        Store store=storeLoginService.getById(id);
        Customer customer = loginService.getByEmail(loginEmail);
        if(customer==null){
            logger.info("顾客未注册信息直接访问餐厅页面"+loginEmail);
            return "redirect:/login/register-info";
        }else{
            model.addAttribute("userName",customer.getUserName());
        }
        model.addAttribute("storeId",id);
        model.addAttribute("storeName", store.getStoreName());
        model.addAttribute("storeEquipmentEatTime", store.getEquipmentEatTime());
        model.addAttribute("storeNotice", store.getNotice());
        model.addAttribute("storeAddress", store.getAddress());
        model.addAttribute("storeCapacity", store.getCapacity());
        model.addAttribute("storePicture", store.getStorePicture());
        model.addAttribute("positionLng", store.getPositionLng());
        model.addAttribute("positionLat", store.getPositionLat());
        Integer monthSales= store.getMonthSales();
        Float score=store.getScore();
        model.addAttribute("storeMonthSales", monthSales==null?0:monthSales);
        model.addAttribute("storeScore", score==null?0.0:score);
        return "enter-store";
    }

    @ResponseBody
    @GetMapping("/getStoresByName/{city}/{storeName}")
    public List<Store> getStoresByName(@PathVariable("city") String city,@PathVariable("storeName") String storeName){
        List<Store> stores=storeLoginService.getStoresByName(city,storeName);
        return stores;
    }
}
