package cn.mrxiao.graduation.controller;

import cn.mrxiao.graduation.beans.Food;
import cn.mrxiao.graduation.beans.Store;
import cn.mrxiao.graduation.service.FoodService;
import cn.mrxiao.graduation.service.StoreLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author mrxiao
 * @date 2020/4/27 - 21:05
 **/
@Controller
@CrossOrigin
public class FoodController {
    @Autowired
    FoodService foodService;
    @Autowired
    StoreLoginService storeLoginService;

    @PostMapping("/submit-food")
    public String addFood(Food food, @RequestParam("foodPictureFile") MultipartFile foodPictureFile, HttpServletRequest request, RedirectAttributes attributes){
        String loginEmail="",uuid="";
        Cookie[] cs=request.getCookies();
        try{
            food.setPictureName(foodService.saveFoodPicture(request,foodPictureFile));
        }catch (Exception e){
            attributes.addFlashAttribute("msg","保存失败");
            return "redirect:add-food";
        }
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail=c.getValue();
            }
        }
        Store store=storeLoginService.getByEmail(loginEmail);
        food.setStoreId(store.getId());
        if(foodService.getByStoreIdAndFoodName(food.getStoreId(),food.getFoodName())!=null){
            Food food1=foodService.getByStoreIdAndFoodName(food.getStoreId(),food.getFoodName());
            uuid=food1.getUuid();
            food.setId(food1.getId());
        }else{
            uuid= UUID.randomUUID().toString().replace("-", "");
        }
        food.setUuid(uuid);
        foodService.updateFood(food);
        foodService.updateStoreFoods(store.getId());
        return "redirect:/store";
    }

    @ResponseBody
    @GetMapping("/changeFoodState/{uuid}/{state}")
    public Food changeFoodState(@PathVariable("uuid") String uuid,@PathVariable("state") Boolean state){
        System.out.println(uuid);
        System.out.println(state);
        Food food=foodService.getByUUid(uuid);
        if(state)
            food.setState(0);//有货
        else
            food.setState(1);//无货
        foodService.updateFood(food);
        foodService.updateStoreFoods(food.getStoreId());
        return food;
    }

    @ResponseBody
    @GetMapping("/deleteFood/{uuid}")
    public Integer deleteFood(@PathVariable("uuid") String uuid){
        Food food=foodService.getByUUid(uuid);
        Integer result=foodService.deleteFood(uuid);
        foodService.updateStoreFoods(food.getStoreId());
        return result;
    }
}
