package cn.mrxiao.graduation.controller;

import cn.mrxiao.graduation.beans.Customer;
import cn.mrxiao.graduation.beans.Food;
import cn.mrxiao.graduation.beans.Orders;
import cn.mrxiao.graduation.beans.Store;
import cn.mrxiao.graduation.service.FoodService;
import cn.mrxiao.graduation.service.LoginService;
import cn.mrxiao.graduation.service.OrdersService;
import cn.mrxiao.graduation.service.StoreLoginService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author mrxiao
 * @date 2020/5/2 - 14:27
 **/
@Controller
@CrossOrigin
public class OrdersController {
    @Autowired
    LoginService loginService;
    @Autowired
    Orders orders;
    @Autowired
    FoodService foodService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    StoreLoginService storeLoginService;
    @Autowired
    Logger logger;
    @Autowired
    SimpleDateFormat simpleDateFormat;

    @ResponseBody
    @PostMapping("/submit-order")
    public String submitOrder(@RequestParam Map<String,String> map, HttpServletRequest request){
        String loginEmail="",time="";
        Cookie[] cs=request.getCookies();
        Food food;Store store;
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail")){
                    loginEmail=c.getValue();
                    break;
                }
            }
        }
        StringBuilder str = new StringBuilder();
        //生成验证码
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        String uuid= UUID.randomUUID().toString().replace("-", "");
        orders.setUuid(uuid);
        Date date = new Date();
        time = simpleDateFormat.format(date);
        orders.setTime(time);
        Customer customer=loginService.getByEmail(loginEmail);
        Integer cusId=customer.getId();
        orders.setCustomerId(cusId);
        orders.setCustomerName(customer.getUserName());
        orders.setCustomerPhoneNumber(customer.getPhoneNumber());
        orders.setPaymentCode(str.toString());
        Set<String> keySet=map.keySet();
        Iterator<String> it=keySet.iterator();
        while (it.hasNext()){
            String key=it.next();
            food=foodService.getByUUid(key);
            orders.setFoodName(food.getFoodName());
            orders.setUnitPrice(food.getPrice());
            orders.setState(0);
            orders.setStoreId(food.getStoreId());
            store=storeLoginService.getById(food.getStoreId());
            orders.setStorePhoneNumber(store.getPhoneNumber());
            orders.setStoreName(store.getStoreName());
            orders.setWechatPicture(store.getWechatPicture());
            orders.setZhifubaoPicture(store.getZhifubaoPicture());
            String value=map.get(key);
            orders.setQuantity(Integer.parseInt(value));
            ordersService.insertOrder(orders);
        }
        ordersService.updateOrdersByStoreId(orders.getStoreId());
        return  "1";
    }

    @GetMapping("/customer-orders")
    public String customerOrders(Model model,HttpServletRequest request){
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
            logger.info("顾客未注册信息直接访问订单管理页面"+loginEmail);
            return "redirect:/login/register-info";
        }else{
            model.addAttribute("loginUser",customer.getUserName());
            model.addAttribute("customerId",customer.getId());
        }
        return "customer-orders";
    }

    @GetMapping("/store-orders")
    public String storeOrders(Model model,HttpServletRequest request){
        String loginEmail = "";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail = c.getValue();
            }
        }
        Store store=storeLoginService.getByEmail(loginEmail);
        if(store==null){
            logger.info("商家未注册信息直接访问订单管理页面"+loginEmail);
            return "redirect:/store-login/register-store-info";
        }else{
            model.addAttribute("storeName",store.getStoreName());
            model.addAttribute("storeId",store.getId());
        }
        return "store-orders";
    }

    @ResponseBody
    @GetMapping("/getAllOrdersByStoreId/{storeId}")
    public List<Orders> getAllOrdersByStoreId(@PathVariable("storeId") Integer storeId){
        List<Orders> orders=ordersService.getOrdersByStoreId(storeId);
        return orders;
    }

    @ResponseBody
    @GetMapping("/getAllOrdersByCustomerId/{cusId}")
    public List<Orders> getAllOrdersByCustomerId(@PathVariable("cusId") Integer cusId){
        List<Orders> orders=ordersService.getOrdersByCusId(cusId);
        return orders;
    }

    @GetMapping("/payment/{uuid}/{total}/{modeOfPayment}")
    public String payment(@PathVariable("uuid") String uuid,@PathVariable("total") String total,@PathVariable("modeOfPayment") String modeOfPayment,HttpServletRequest request){
        String customerEmail="";
        Cookie[] cs = request.getCookies();
        if (cs != null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail")){
                    customerEmail = c.getValue();
                    break;
                }
            }
        }
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        ordersService.paymentUpdateOrder(uuid,time,1);
        List<Orders> orders=ordersService.getOrderByUuid(uuid);
        ordersService.sendPaymentEmail(customerEmail,orders,total,modeOfPayment);
        ordersService.updateOrdersByStoreId(orders.get(0).getStoreId());
        return "redirect:/customer-orders";
    }
    @GetMapping("/affirmOrder/{uuid}")
    public String affirmOrder(@PathVariable("uuid")String uuid){
        ordersService.updateOrder(uuid,2);
        List<Orders> orders=ordersService.getOrderByUuid(uuid);
        if(orders.size()!=0){
            ordersService.updateOrdersByStoreId(orders.get(0).getStoreId());
            ordersService.sendaffirmOrderEmail(orders);
        }
        return "redirect:/store-orders";
    }
    @GetMapping("/sendEmail/{uuid}")
    public String sendEmail(@PathVariable("uuid") String uuid,HttpServletRequest request){
        ordersService.updateOrder(uuid,3);
        List<Orders> orders=ordersService.getOrderByUuid(uuid);
        if(orders.size()!=0){
            ordersService.updateOrdersByStoreId(orders.get(0).getStoreId());
            ordersService.sendEatEmail(orders);
        }
        return "redirect:/store-orders";
    }

    // second（秒）, minute（分）, hour（时）, day of month（日）, month（月）, and day of week（周几）
    @Scheduled(cron="0/1 * * * * ?")//从0秒启动，每1秒执行一次
    public void deleteNotPaymentOrder(){
        Date date = new Date();
        String time = simpleDateFormat.format(date.getTime() - 60*1000);
        ordersService.deleteNotPaymentOrder(time);
    }

    @Scheduled(cron="0 0 3 * * ?")//每天凌晨三点自动删除商家未确认的订单
    public void deleteNotAffirmOrder(){
        ordersService.deleteNotAffirmOrder();
    }

    @Scheduled(cron="0 0 22 * * ?")//每天晚上10点自动提醒商家进入平台确认订单
    public void noticeAffirmOrder(){
        ordersService.noticeAffirmOrder();
    }

}
