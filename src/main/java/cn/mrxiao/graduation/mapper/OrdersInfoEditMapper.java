package cn.mrxiao.graduation.mapper;

import cn.mrxiao.graduation.beans.Orders;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mrxiao
 * @date 2020/5/3 - 0:17
 **/
@Mapper
@Repository
public interface OrdersInfoEditMapper {

    @Options(useGeneratedKeys=true,keyProperty="id")
    @Insert("insert into orders(uuid,store_name,store_phone_number,customer_name,customer_phone_number,wechat_picture,zhifubao_picture,payment_code,food_name,unit_price,quantity,time,state,store_id,customer_id) " +
            "values(#{uuid},#{storeName},#{storePhoneNumber},#{customerName},#{customerPhoneNumber},#{wechatPicture},#{zhifubaoPicture},#{paymentCode},#{foodName},#{unitPrice},#{quantity},#{time},#{state},#{storeId},#{customerId})")
    public Integer insertOrder(Orders orders);

    @Update("update orders set state=#{state} where uuid=#{uuid}")
    public Integer updateOrder(@Param("uuid")String uuid,@Param("state")Integer state);

    @Update("update orders set state=#{state},time=#{time} where uuid=#{uuid}")
    public Integer paymentUpdateOrder(@Param("uuid")String uuid,@Param("time")String time,@Param("state")Integer state);

    @Select("select * from orders where uuid=#{uuid}")
    public List<Orders> getOrders(String uuid);

    @Select("select * from orders where customer_id=#{cusId} order by time desc")
    public List<Orders> getOrdersByCusId(Integer cusId);

    @Select("select * from orders where store_id=#{storeId} and state!=0 order by time desc")
    public List<Orders> getOrdersByStoreId(Integer storeId);

    @Delete("delete from orders where state=0 and time<#{time}")
    public Integer deleteNotPaymentOrder(String time);

    @Delete("delete from orders where state=1")
    public Integer deleteNotAffirmOrder();

    @Select("select * from orders where state=1 group by store_id")
    public List<Orders> noticeAffirmOrder();
}
