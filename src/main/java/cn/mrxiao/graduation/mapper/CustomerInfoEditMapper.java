package cn.mrxiao.graduation.mapper;

import cn.mrxiao.graduation.beans.Customer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author mrxiao
 * @date 2020/4/16 - 23:03
 **/
@Mapper
@Repository
public interface CustomerInfoEditMapper {

    @Options(useGeneratedKeys=true,keyProperty="id")
    @Insert("insert into customer(user_name,email,phone_number,position_lat,position_lng,city,address) values(#{userName},#{email},#{phoneNumber},#{positionLat},#{positionLng},#{city},#{address})")
    public Integer insertInfo(Customer customer);

    @Update("update customer set user_name=#{userName},phone_number=#{phoneNumber},position_lat=#{positionLat},position_lng=#{positionLng},city=#{city},address=#{address} where email=#{email}")
    public Integer updateInfo(Customer customer);

    @Select("select * from customer where email=#{email}")
    public Customer getByEmail(String email);

    @Select("select * from customer where id=#{id}")
    public Customer getById(Integer id);
}
