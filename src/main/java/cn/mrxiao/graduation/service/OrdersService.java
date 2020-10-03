package cn.mrxiao.graduation.service;

import cn.mrxiao.graduation.beans.Customer;
import cn.mrxiao.graduation.beans.Orders;
import cn.mrxiao.graduation.mapper.OrdersInfoEditMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mrxiao
 * @date 2020/5/3 - 0:16
 **/
@Service
public class OrdersService {
    @Autowired
    Logger logger;
    @Autowired
    OrdersInfoEditMapper ordersInfoEditMapper;
    @Autowired
    StoreLoginService storeLoginService;
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    SimpleMailMessage message;
    @Autowired
    LoginService loginService;

    public List<Orders> insertOrder(Orders orders){
        ordersInfoEditMapper.insertOrder(orders);
        List<Orders> orders1=ordersInfoEditMapper.getOrders(orders.getUuid());
        logger.info("将新的订单信息插入数据库"+orders1);
        return orders1;
    }

    @CachePut(cacheNames = "orders",key="#uuid")
    public List<Orders> updateOrder(String uuid,Integer state){
        ordersInfoEditMapper.updateOrder(uuid,state);
        List<Orders> orders1=ordersInfoEditMapper.getOrders(uuid);
        logger.info("更新了订单"+uuid+"的状态编码为"+state+","+orders1);
        return orders1;
    }

    @CachePut(cacheNames = "orders",key="#uuid")
    public List<Orders> paymentUpdateOrder(String uuid,String time,Integer state){
        ordersInfoEditMapper.paymentUpdateOrder(uuid,time,state);
        List<Orders> orders1=ordersInfoEditMapper.getOrders(uuid);
        logger.info("更新了订单"+uuid+"的状态编码为"+state+","+orders1);
        return orders1;
    }
    @Cacheable(cacheNames = "orders",key="#uuid")
    public List<Orders> getOrderByUuid(String uuid){
        List<Orders> orders1=ordersInfoEditMapper.getOrders(uuid);
        logger.info("从数据库中获取订单信息加入缓存"+orders1);
        return orders1;
    }
    @CachePut(cacheNames = "orders",key="#uuid",unless = "#result==null")
    public List<Orders> updateOrderByUuid(String uuid){
        List<Orders> orders1=ordersInfoEditMapper.getOrders(uuid);
        logger.info("从数据库中获取订单信息加入缓存"+orders1);
        return orders1;
    }
    public List<Orders> getOrdersByCusId(Integer cusId){
        List<Orders> orders=ordersInfoEditMapper.getOrdersByCusId(cusId);
        logger.info("从数据库获取用户id为"+cusId+"的订单列表");
        return orders;
    }
    @CachePut(cacheNames = "ordersByStoreId",key="#storeId")
    public List<Orders> updateOrdersByStoreId(Integer storeId){
        List<Orders> orders=ordersInfoEditMapper.getOrdersByStoreId(storeId);
        logger.info("更新了缓存中商家id为"+storeId+"的订单列表");
        return orders;
    }
    @Cacheable(cacheNames = "ordersByStoreId",key="#storeId")
    public List<Orders> getOrdersByStoreId(Integer storeId){
        List<Orders> orders=ordersInfoEditMapper.getOrdersByStoreId(storeId);
        logger.info("更新了缓存中商家id为"+storeId+"的订单列表");
        return orders;
    }

    public Integer deleteNotPaymentOrder(String time){
        return ordersInfoEditMapper.deleteNotPaymentOrder(time);
    }


    public Integer deleteNotAffirmOrder(){
        return ordersInfoEditMapper.deleteNotAffirmOrder();
    }

    @Async
    public void noticeAffirmOrder(){
        List<Orders> orders=ordersInfoEditMapper.noticeAffirmOrder();
        for(int i=0;i<orders.size();i++){
            message.setSubject("请进入平台确认订单");
            message.setText("您的餐厅在今天3~22点之内在本平台接过单，但未确认订单。如订单属实请进入平台120.79.205.109确认订单，如订单不属实则请忽略此邮件。提醒：因未确认订单被顾客反馈将暂停餐厅在此平台的展示许可");
            message.setTo(storeLoginService.getById(orders.get(i).getStoreId()).getEmail());
            message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
            mailSender.send(message);
        }
    }

    @Async
    public void sendPaymentEmail(String customerEmail, List<Orders> orders,String total,String modeOfPayment){
        String foodList="";
        for(int i=0;i<orders.size();i++){
            if(i==orders.size()-1){
                foodList=foodList+orders.get(i).getFoodName()+"×"+orders.get(i).getQuantity()+"份";
            }else{
                foodList=foodList+orders.get(i).getFoodName()+"×"+orders.get(i).getQuantity()+"份、";
            }
        }

        String storeEmail=storeLoginService.getById(orders.get(0).getStoreId()).getEmail();
        //顾客邮件设置
        message.setSubject("下单成功");
        message.setText("您在“"+orders.get(0).getStoreName()+"”餐厅点的菜单已通知餐厅，请等待商家确认订单或拨打商家电话"+orders.get(0).getStorePhoneNumber()+"提醒其备餐。取餐码为"+orders.get(0).getPaymentCode()+"，具体菜单：“"+foodList+"”。");
        message.setTo(customerEmail);
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);

        //商家邮件设置
        message.setSubject("点餐通知");
        if(modeOfPayment.equals("wechat")){
            message.setText("顾客“"+orders.get(0).getCustomerName()+"”在您的餐厅下了订单，已通过微信扫码付款"+total+"，付款备注为“"
                    +orders.get(0).getPaymentCode()+"”，即为取餐码。请核对订单无误后进入平台点击“确认订单无误”按钮即发送邮件告诉顾客订单已确认或拨打顾客电话"
                    +orders.get(0).getCustomerPhoneNumber()+"通知。尽快备好餐，备好后可通过系统的“发送用餐邮件”功能或拨打顾客电话"
                    +orders.get(0).getCustomerPhoneNumber()+"通知顾客用餐。具体菜单：“"+foodList+"”。");
        }else{
            message.setText("顾客“"+orders.get(0).getCustomerName()+"”在您的餐厅下了订单，已通过支付宝扫码付款"+total+"，付款备注为“"
                    +orders.get(0).getPaymentCode()+"”，即为取餐码。请核对订单无误后进入平台点击“确认订单无误”按钮即发送邮件告诉顾客订单已确认或拨打顾客电话"
                    +orders.get(0).getCustomerPhoneNumber()+"通知。尽快备好餐，备好后可通过系统的“发送用餐邮件”功能或拨打顾客电话"
                    +orders.get(0).getCustomerPhoneNumber()+"通知顾客用餐。具体菜单：“"+foodList+"”。");
        }
        message.setTo(storeEmail);
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);
    }

    @Async
    public void sendaffirmOrderEmail(List<Orders> orders){
        String foodList="";
        for(int i=0;i<orders.size();i++){
            if(i==orders.size()-1){
                foodList=foodList+orders.get(i).getFoodName()+"×"+orders.get(i).getQuantity()+"份";
            }else{
                foodList=foodList+orders.get(i).getFoodName()+"×"+orders.get(i).getQuantity()+"份、";
            }
        }

        Customer customer=loginService.getById(orders.get(0).getCustomerId());
        //顾客邮件设置
        message.setSubject("商家已确认订单");
        message.setText("您在“"+orders.get(0).getStoreName()+"”餐厅点的菜单商家已确认订单。请等待用餐邮件提醒或商家电话提醒用餐。取餐码为"+orders.get(0).getPaymentCode()+"，具体菜单：“"+foodList+"”。");
        message.setTo(customer.getEmail());
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);
    }
    @Async
    public void sendEatEmail(List<Orders> orders){
        String customerEmail="",foodList="";
        for(int i=0;i<orders.size();i++){
            if(i==orders.size()-1){
                foodList=foodList+orders.get(i).getFoodName()+"×"+orders.get(i).getQuantity()+"份";
            }else{
                foodList=foodList+orders.get(i).getFoodName()+"×"+orders.get(i).getQuantity()+"份、";
            }
        }
        customerEmail=loginService.getById(orders.get(0).getCustomerId()).getEmail();
        //顾客邮件设置
        message.setSubject("请到餐厅用餐");
        message.setText("您在“"+orders.get(0).getStoreName()+"”餐厅点的菜单餐厅已备好餐，取餐码为"+orders.get(0).getPaymentCode()+"，请到餐厅用餐。具体菜单：“"+foodList+"”。");
        message.setTo(customerEmail);
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);
    }

}
