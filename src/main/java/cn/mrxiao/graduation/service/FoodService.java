package cn.mrxiao.graduation.service;

import cn.mrxiao.graduation.beans.Food;
import cn.mrxiao.graduation.beans.Store;
import cn.mrxiao.graduation.mapper.FoodInfoEditMapper;
import cn.mrxiao.graduation.mapper.StoreInfoEditMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collection;
import java.util.UUID;

/**
 * @author mrxiao
 * @date 2020/4/27 - 21:13
 **/
@Service
public class FoodService {
    @Autowired
    Logger logger;
    @Autowired
    FoodInfoEditMapper foodInfoEditMapper;
    @Autowired
    StoreInfoEditMapper storeInfoEditMapper;
    public String saveFoodPicture(HttpServletRequest request, MultipartFile foodPictureFile) throws Exception{
        String fileName="foodPictureFile";
        String path="/graduate-project/upload-data/foodPictures/";//request.getSession().getServletContext().getRealPath("/foodPictures/");
        File file=new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        String uuid= UUID.randomUUID().toString().replace("-", "");
        fileName=fileName+"_"+uuid+"_"+foodPictureFile.getOriginalFilename();
        foodPictureFile.transferTo(new File(path+fileName));
        return fileName;
    }

    @CachePut(cacheNames = "foods",key = "#food.uuid")
    public Food updateFood(Food food){
        if(foodInfoEditMapper.updateFood(food)!=1)
            foodInfoEditMapper.insertFood(food);
        Food food1=foodInfoEditMapper.getByUUid(food.getUuid());
        logger.info("update将食品信息插入数据库并更新进缓存中"+food1);
        return food1;
    }

    @Cacheable(cacheNames = "store-foods",key = "#storeId+'-'+#foodName",unless="#result==null")
    public Food getByStoreIdAndFoodName( Integer storeId, String foodName){
        Food food=foodInfoEditMapper.getByStoreIdAndFoodName(storeId,foodName);
        logger.info("根据商家storeId和食品名称foodName查询食品信息并存入缓存中"+food);
        return food;
    }

    @Cacheable(cacheNames = "foods",key = "#uuid",unless = "#result==null")
    public Food getByUUid(String uuid){
        Food food=foodInfoEditMapper.getByUUid(uuid);
        logger.info("根据uuid查询食品信息并存入缓存中"+food);
        return food;
    }

    @CachePut(cacheNames = "storeFoods",key = "#id")
    public Collection<Food> updateStoreFoods(Integer id){
        Collection<Food> foods=foodInfoEditMapper.getByStoreId(id);
        logger.info("更新了id为"+id+"的商家的菜单信息");
        return foods;
    }

    @Cacheable(cacheNames = "storeFoods",key = "#id",unless = "#result==null")
    public Collection<Food> getStoreFoods(Integer id){
        Collection<Food> foods=foodInfoEditMapper.getByStoreId(id);
        logger.info("更新了id为"+id+"的商家的菜单信息");
        return foods;
    }

    @CacheEvict(cacheNames = "foods",key = "#uuid")
    public Integer deleteFood(String uuid){
        Food food=foodInfoEditMapper.getByUUid(uuid);
        Store store=storeInfoEditMapper.getById(food.getStoreId());
        logger.info("删除了“"+store.getStoreName()+"”商家菜单中的“"+food.getFoodName()+"”菜品");
        Integer result=foodInfoEditMapper.deleteFoodByUuid(uuid);
        return result;
    }
}
