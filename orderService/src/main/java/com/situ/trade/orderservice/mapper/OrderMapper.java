package com.situ.trade.orderservice.mapper;

import com.situ.trade.commons.domian.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert({
            "insert into orders",
            "(amount,goods_id,user_id,address,seller_id)",
            "values ",
            "(#{amount},#{goodsId},#{userId},#{address},#{sellerId})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insert(Order order);

    @Update({
            "update orders",
            "set status=#{status}",
            "where order_id=#{orderId}"
    })
    int update(Order order);

    @Delete({
            "delete from orders",
            "where order_id=#{orderId}"
    })
    int delete(Integer orderId);

    @Select({
            "select * from orders",
            "where order_id=#{orderId}"
    })
    Order selectById(Integer orderId);

    @Select({
            "<script>",
            "select * from orders",
            "<where>",
            "<if test='status != null'>and status = #{status}</if>",
            "<if test='userId != null'>and user_id = #{userId}</if>",
            "<if test='goodsId != null'>and goods_id = #{goodsId}</if>",
            "<if test='createTime != null'>and create_time = #{createTime}</if>",
            "<if test='sellerId != null'>and seller_id = #{sellerId}</if>",
            "</where>",
            "</script>"
    })
    List<Order> select(Order order);

}
