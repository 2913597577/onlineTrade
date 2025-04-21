package com.situ.trade.orderservice.mapper;

import com.situ.trade.commons.domian.entity.Carts;
import com.situ.trade.commons.domian.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartsMapper {
    @Insert({
            "insert into carts",
            "(quantity,goods_id,user_id,price)",
            "values ",
            "(#{quantity},#{goodsId},#{userId},#{price})"
    })
    int insert(Carts carts);

    @Update({
            "<script>",
            "update carts",
            "<set>",
            "<if test='quantity!=null'>quantity=#{quantity},</if>",
            "<if test='price!=null'>price=#{price},</if>",
            "</set>",
            "where user_id=#{userId} and goods_id=#{goodsId}",
            "</script>"
    })
    int update(Carts carts);

    @Delete({
            "delete from carts",
            "where cart_id=#{cartId}"
    })
    int delete(Integer cartId);

    @Select({
            "select * from carts",
            "where user_id=#{userId} and goods_id=#{goodsId}",
    })
    Carts selectByUserIdAndGoodsId(Integer userId, Integer goodsId);

    @Select({
            "select * from carts",
            "where user_id=#{userId}",
    })
    List<Carts> selectByUserId(Integer userId);

    @Select({
            "select * from carts",
            "where user_id=#{userId}"
    })
    List<Carts> select(Carts carts);
    @Select({
            "select * from carts",
            "where user_id=#{userId}"
    })
    List<Carts> selectForPage(Carts carts);

}
