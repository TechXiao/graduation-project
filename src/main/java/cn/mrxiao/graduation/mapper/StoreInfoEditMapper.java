package cn.mrxiao.graduation.mapper;

import cn.mrxiao.graduation.beans.Store;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author mrxiao
 * @date 2020/4/17 - 23:28
 **/
@Mapper
@Repository
public interface StoreInfoEditMapper {

    @Options(useGeneratedKeys=true,keyProperty="id")
    @Insert("insert into store(email,phone_number,store_name,capacity,equipment_eat_time,state,store_picture" +
            ",license_picture,identity_picture,wechat_picture,zhifubao_picture" +
            ",city,address,position_lat,position_lng,notice) values(#{email},#{phoneNumber},#{storeName},#{capacity},#{equipmentEatTime},#{state}" +
            ",#{storePicture},#{licensePicture},#{identityPicture},#{wechatPicture},#{zhifubaoPicture},#{city},#{address},#{positionLat},#{positionLng},#{notice})")
    public Integer insertInfo(Store store);

    @Update("update store set phone_number=#{phoneNumber},store_name=#{storeName},capacity=#{capacity},equipment_eat_time=#{equipmentEatTime}" +
            ",state=#{state},store_picture=#{storePicture}" +
            ",license_picture=#{licensePicture},identity_picture=#{identityPicture},wechat_picture=#{wechatPicture},zhifubao_picture=#{zhifubaoPicture}" +
            ",city=#{city},address=#{address},position_lat=#{positionLat},position_lng=#{positionLng},notice=#{notice}" +
            " where email=#{email}")
    public Integer updateInfo(Store store);

    @Select("select * from store where email=#{email}")
    public Store getByEmail(String email);

    @Select("select * from store where city=#{city} and license='1'")
    public Collection<Store> getAllStoresByCity(String city);

    @Select("select * from store where id=#{id}")
    public Store getById(Integer id);

    @Select("select * from store order by id desc")
    public List<Store> getAllStore();

    @Update("update store set license=#{license} where id=#{storeId}")
    public Integer changeStoreLicenseByStoreId(@Param("storeId") Integer storeId,@Param("license") Integer license);

    @Select("select * from store where city=#{city} and store_name like #{storeName}")
    public List<Store> getStoresByName(@Param("city") String city,@Param("storeName")String storeName);
}
