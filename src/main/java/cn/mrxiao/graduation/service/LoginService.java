package cn.mrxiao.graduation.service;

import cn.mrxiao.graduation.beans.Customer;
import cn.mrxiao.graduation.mapper.CustomerInfoEditMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author mrxiao
 * @date 2020/4/14 - 19:27
 **/
@Service
public class LoginService {
    //日志记录器
    @Autowired
    Logger logger;
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    CustomerInfoEditMapper customerInfoEditMapper;
    @Autowired
    RedisCacheManager cacheManager;
    @Autowired
    SimpleMailMessage message;
    /**
     * 获取验证码service
     * @param email
     * @return
     * @throws Exception
     */
    public String sendSecurityCode(String email) throws Exception {
        StringBuilder str = new StringBuilder();
        //生成验证码
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        //邮件设置
        message.setSubject("顾客-验证码");
        message.setText("您的验证码是"+str.toString()+"，在15分钟之内有效。");
        message.setTo(email);
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);
        return str.toString();
    }

    public boolean checkCustomerExist(String email){
        Cache customers=cacheManager.getCache("customers");
        if(customers.get(email)!=null){
            logger.info("缓存--判断顾客是已注册"+email+customers.get(email));
            return true;
        } else if(customerInfoEditMapper.getByEmail(email)!=null){
            Customer customer=customerInfoEditMapper.getByEmail(email);
            customers.put(email,customer);
            logger.info("数据库--判断顾客是已注册"+email);
            logger.info("用email作为key将顾客信息存入缓存中"+customer);
            return true;
        }else
            return false;
    }

    @CachePut(cacheNames = "customers",key="#customer.email")
    public Customer updateInfo(Customer customer){
        if(customerInfoEditMapper.updateInfo(customer)!=1)
            customerInfoEditMapper.insertInfo(customer);
        Customer customer1=customerInfoEditMapper.getByEmail(customer.getEmail());
        logger.info("update将顾客信息插入数据库并更新进缓存中"+customer1);
        return customer1;
    }

    @Cacheable(cacheNames = "customers",key="#email",unless="#result==null")
    public Customer getByEmail(String email){
        Customer customer=customerInfoEditMapper.getByEmail(email);
        logger.info("从数据库根据email查询顾客信息从数据库并存入缓存中(除非为空)"+customer);
        return customer;
    }

    @Cacheable(cacheNames = "customersById",key="#id",unless="#result==null")
    public Customer getById(Integer id){
        Customer customer=customerInfoEditMapper.getById(id);
        logger.info("从数据库根据id查询顾客信息从数据库并存入缓存中(除非为空)"+customer);
        return customer;
    }
}
