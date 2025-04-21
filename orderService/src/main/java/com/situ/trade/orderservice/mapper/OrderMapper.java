package com.situ.trade.orderservice.mapper;

import com.situ.trade.commons.domian.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert({
            "insert into orders",
            "(amount,goods_id,user_id)",
            "values ",
            "(#{amout},#{goodsId},#{userId})"
    })
    int insert(Order order);

    @Update({
            "update orders",
            "set status=#{status}"
    })
    int update(Order order);

    @Delete({
            "delete from orders",
            "where order_id=#{orderId}"
    })
    int delete(Order order);

    @Select({
            "select * from orders",
            "where order_id=#{orderId}"
    })
    Order selectById(Integer orderId);

    @Select({
            "select * from orders",
    })
    List<Order> selectBy(Order order);
}
