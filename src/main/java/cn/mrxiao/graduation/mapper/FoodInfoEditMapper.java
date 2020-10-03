package cn.mrxiao.graduation.mapper;

import cn.mrxiao.graduation.beans.Food;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author mrxiao
 * @date 2020/4/27 - 21:14
 **/
@Mapper
@Repository
public interface FoodInfoEditMapper {
    @Options(useGeneratedKeys=true,keyProperty="id")
    @Insert("insert into food(uuid,food_name,description,price,food_group,state,picture_name,store_id) values(#{uuid},#{foodName},#{description},#{price},#{foodGroup},#{state},#{pictureName},#{storeId})")
    public Integer insertFood(Food food);

    @Update("update food set food_name=#{foodName},description=#{description},price=#{price},food_group=#{foodGroup},state=#{state},picture_name=#{pictureName},store_id=#{storeId} where uuid=#{uuid}")
    public Integer updateFood(Food food);

    @Select("select * from food where store_id=#{id} order by food_group asc")
    public Collection<Food> getByStoreId(Integer id);

    @Select("select * from food where uuid=#{uuid}")
    public Food getByUUid(String uuid);

    @Select("select * from food where store_id=#{storeId} and food_name=#{foodName}")
    public Food getByStoreIdAndFoodName(@Param("storeId")Integer storeId,@Param("foodName")String foodName);

    @Delete("delete from food where uuid=#{uuid}")
    public Integer deleteFoodByUuid(String uuid);
}
