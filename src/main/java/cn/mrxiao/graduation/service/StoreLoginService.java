package cn.mrxiao.graduation.service;

import cn.mrxiao.graduation.beans.Store;
import cn.mrxiao.graduation.mapper.StoreInfoEditMapper;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @author mrxiao
 * @date 2020/4/17 - 23:27
 **/
@Service
public class StoreLoginService {
    //日志记录器
    @Autowired
    Logger logger;
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    StoreInfoEditMapper storeInfoEditMapper;
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
        message.setSubject("商家-验证码");
        message.setText("您的验证码是"+str.toString()+"，在15分钟之内有效。");
        message.setTo(email);
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);
        return str.toString();
    }


    /**
     * 保存餐厅照片
     * @param storePicture 餐厅照片文件
     * @return  餐厅照片名称
     * @throws Exception
     */
    public String saveStorePicture(HttpServletRequest request,MultipartFile storePicture) throws Exception{
        String fileName="storePicture";
        String path="/graduate-project/upload-data/storePictures/";//request.getSession().getServletContext().getRealPath("/storePictures/");
        File file=new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        String uuid= UUID.randomUUID().toString().replace("-", "");
        fileName=fileName+"_"+uuid+"_"+storePicture.getOriginalFilename();
        storePicture.transferTo(new File(path+fileName));
        return fileName;
    }

    /**
     * 保存营业执照
     * @param licensePicture 营业执照文件
     * @return 营业执照文件名称
     * @throws Exception
     */
    public String saveLicensePicture(HttpServletRequest request,MultipartFile licensePicture) throws Exception{
        String fileName="licensePicture";
        String path="/graduate-project/upload-data/licensePictures/";//request.getSession().getServletContext().getRealPath("/licensePictures/");
        File file=new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        String uuid= UUID.randomUUID().toString().replace("-", "");
        fileName=fileName+"_"+uuid+"_"+licensePicture.getOriginalFilename();
        licensePicture.transferTo(new File(path+fileName));
        return fileName;
    }

    /**
     * 保存老板身份照片
     * @param identityPicture 身份照片
     * @return  身份照片名称
     * @throws Exception
     */
    public String saveIdentityPicture(HttpServletRequest request,MultipartFile identityPicture) throws Exception{
        String fileName="identityPicture";
        String path="/graduate-project/upload-data/identityPictures/";//request.getSession().getServletContext().getRealPath("/identityPictures/");
        File file=new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        String uuid= UUID.randomUUID().toString().replace("-", "");
        fileName=fileName+"_"+uuid+"_"+identityPicture.getOriginalFilename();
        identityPicture.transferTo(new File(path+fileName));
        return fileName;
    }
    public String saveWechatPicture(HttpServletRequest request,MultipartFile wechatPicture) throws Exception{
        String fileName="wechatPicture";
        String path="/graduate-project/upload-data/wechatPictures/";//request.getSession().getServletContext().getRealPath("/wechatPictures/");
        File file=new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        String uuid= UUID.randomUUID().toString().replace("-", "");
        fileName=fileName+"_"+uuid+"_"+wechatPicture.getOriginalFilename();
        wechatPicture.transferTo(new File(path+fileName));
        return fileName;
    }
    public String saveZhiFuBaoPicture(HttpServletRequest request,MultipartFile zhifubaoPicture) throws Exception{
        String fileName="zhifubaoPicture";
        String path="/graduate-project/upload-data/zhifubaoPictures/";//request.getSession().getServletContext().getRealPath("/zhifubaoPictures/");
        File file=new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        String uuid= UUID.randomUUID().toString().replace("-", "");
        fileName=fileName+"_"+uuid+"_"+zhifubaoPicture.getOriginalFilename();
        zhifubaoPicture.transferTo(new File(path+fileName));
        return fileName;
    }
    @CachePut(cacheNames = "stores",key="#store.email")
    public Store updateInfo(Store store){
        if(storeInfoEditMapper.updateInfo(store)!=1)
            storeInfoEditMapper.insertInfo(store);
        Store store1=storeInfoEditMapper.getByEmail(store.getEmail());
        logger.info("update将商家信息插入数据库并更新进缓存中"+store1);
        return store1;
    }

    public boolean checkStoreExist(String email){
        Cache stores=cacheManager.getCache("stores");
        if(stores.get(email)!=null){
            logger.info("缓存--判断商家是否已注册"+email);
            return true;
        }else if(storeInfoEditMapper.getByEmail(email)!=null){
            Store store=storeInfoEditMapper.getByEmail(email);
            stores.put(email,store);
            logger.info("从数据库根据email为"+email+"查询商家信息，并将商家信息存入缓存中"+store);
            logger.info("数据库--判断商家是否已注册"+email);
            return true;
        }else
            return false;
    }

    @Cacheable(cacheNames = "stores",key="#email",unless="#result==null")
    public Store getByEmail(String email){
        Store store=storeInfoEditMapper.getByEmail(email);
        logger.info("从数据库根据email为"+email+"查询商家信息并存入缓存中(除非为空)"+store);
        return store;
    }

    @Cacheable(cacheNames = "store-list",key="#city+'-store-list'",unless="#result==null")
    public Collection<Store> getAllStoresByCity(String city){
        Collection<Store> stores=storeInfoEditMapper.getAllStoresByCity(city);
        logger.info("更新了缓存中的“"+city+"”城市中的商家列表");
        return stores;
    }

    @CachePut(cacheNames = "store-list",key="#city+'-store-list'",unless = "#result==null")
    public Collection<Store> updateCityStoreList(String city){
        Collection<Store> stores=storeInfoEditMapper.getAllStoresByCity(city);
        logger.info("更新了缓存中的“"+city+"”城市中的商家列表");
        return stores;
    }

    @CachePut(cacheNames = "storesById",key="#id",unless="#result==null")
    public Store updateStoreById(Integer id){
        Store store=storeInfoEditMapper.getById(id);
        logger.info("从数据库根据id查询商家信息并存入缓存中(除非为空)"+store);
        return store;
    }

    @Cacheable(cacheNames = "storesById",key="#id",unless="#result==null")
    public Store getById(Integer id){
        Store store=storeInfoEditMapper.getById(id);
        logger.info("从数据库根据id查询商家信息并存入缓存中(除非为空)"+store);
        return store;
    }

    /**
     * 发送商家注册成功邮件
     * @param email
     * @return
     * @throws Exception
     */
    @Async
    public void sendStoreSuccessRegister(String email) throws Exception {
        Store store=storeInfoEditMapper.getByEmail(email);
        //邮件设置
        message.setSubject("商家-注册成功");
        message.setText("您的餐厅“"+store.getStoreName()+"”已本平台注册成功，请添加菜品。等待审核通过");
        message.setTo(email);
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);

        //邮件设置
        message.setSubject("有商家注册进平台");
        message.setText("餐厅“"+store.getStoreName()+"”已在本平台注册成功。记得审核");
        message.setTo("960254430@qq.com");
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);

        return;
    }
    @Async
    public void sendStoreEditInfo(String email) throws Exception {
        Store store=storeInfoEditMapper.getByEmail(email);
        //邮件设置
        message.setSubject("商家-修改信息");
        message.setText("您的餐厅“"+store.getStoreName()+"”已修改了信息，请等待重新审核。");
        message.setTo(email);
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);

        //邮件设置
        message.setSubject("有商家修改了信息");
        message.setText("餐厅“"+store.getStoreName()+"”修改了信息。记得审核");
        message.setTo("960254430@qq.com");
        message.setFrom("Food在线-线上点餐，线下用（取）餐<jinbing.xiao@foxmail.com>");
        mailSender.send(message);
        return;
    }

    @CachePut(cacheNames = "allStore",key="'all'")
    public List<Store> updateAllStore(){
        List<Store> stores=storeInfoEditMapper.getAllStore();
        logger.info("更新了缓存中的所有商家列表");
        return stores;
    }
    @Cacheable(cacheNames = "allStore",key="'all'",unless = "#result==null")
    public List<Store> getAllStore(){
        List<Store> stores=storeInfoEditMapper.getAllStore();
        logger.info("从数据库中获取所有商家列表添加到了缓存中");
        return stores;
    }

    public Integer changeStoreLicenseByStoreId(Integer storeId,Integer license){
        logger.info("将id为"+storeId+"的餐厅许可编码设为"+license);
        return storeInfoEditMapper.changeStoreLicenseByStoreId(storeId,license);
    }

    public List<Store> getStoresByName(String city,String storeName){
        List<Store> stores=storeInfoEditMapper.getStoresByName(city,"%"+storeName+"%");
        return stores;
    }
}
