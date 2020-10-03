package cn.mrxiao.graduation.controller;

import cn.mrxiao.graduation.beans.Food;
import cn.mrxiao.graduation.beans.Store;
import cn.mrxiao.graduation.service.FoodService;
import cn.mrxiao.graduation.service.StoreLoginService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Collection;

/**
 * @author mrxiao
 * @date 2020/4/23 - 21:14
 **/
@Controller
@CrossOrigin
public class StoreController {
    //日志记录器
    @Autowired
    Logger logger;
    @Autowired
    StoreLoginService storeLoginService;
    @Autowired
    FoodService foodService;

    @GetMapping("/store")
    public String main(HttpServletRequest request, Model model, RedirectAttributes attributes, HttpServletResponse response){
        String loginEmail = "",sessionId="";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail = c.getValue();
            }
        }

        Store store = storeLoginService.getByEmail(loginEmail);
        if(store!=null){
            model.addAttribute("storeId",store.getId());
            model.addAttribute("storeName", store.getStoreName());
            model.addAttribute("storeEquipmentEatTime", store.getEquipmentEatTime());
            model.addAttribute("storeNotice", store.getNotice());
            model.addAttribute("storeAddress", store.getAddress());
            model.addAttribute("storeCapacity", store.getCapacity());
            model.addAttribute("storePicture", store.getStorePicture());
            Integer monthSales= store.getMonthSales();
            Float score=store.getScore();
            model.addAttribute("storeMonthSales", monthSales==null?0:monthSales.toString());
            model.addAttribute("storeScore", score==null?0.0:score.toString());
            Integer state=store.getState();
            model.addAttribute("storeState",state);
        }else {
            logger.info("商家未注册信息直接访问餐厅首页"+loginEmail);
            return "redirect:/store-login/register-store-info";
        }
        return "store";
    }

    @ResponseBody
    @GetMapping("/changeState/{state}")
    public Integer changeState(HttpServletRequest request,@PathVariable("state") Integer state){
        boolean loginJudge = false;
        String loginEmail = "";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail = c.getValue();
            }
        }
        Store store = storeLoginService.getByEmail(loginEmail);
        store.setState(state);
        storeLoginService.updateInfo(store);
        storeLoginService.updateCityStoreList(store.getCity());
        storeLoginService.updateStoreById(store.getId());
        return state;
    }

    @GetMapping("/add-food")
    public String addFood(HttpServletRequest request, Model model){
        boolean loginJudge = false;
        String loginEmail = "";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail")){
                    loginEmail = c.getValue();
                    break;
                }
            }
        }
        Store store = storeLoginService.getByEmail(loginEmail);
        if(store==null){
            logger.info("商家未注册信息直接访问添加菜品页面"+loginEmail);
            return "redirect:/store-login/register-store-info";
        }
        model.addAttribute("loginUser", store.getStoreName());
        return "add-food";
    }

    @ResponseBody
    @GetMapping("/getStoreFoods/{id}")
    public Collection<Food> getStoreFoods(@PathVariable("id") Integer id){
        Collection<Food> foods=foodService.getStoreFoods(id);
        return foods;
    }

    @ResponseBody
    @GetMapping("/getStoreById/{id}")
    public Store getStoreById(@PathVariable("id") Integer id){
        Store store=storeLoginService.getById(id);
        return store;
    }
}
