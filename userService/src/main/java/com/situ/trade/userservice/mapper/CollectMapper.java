package com.situ.trade.userservice.mapper;

import com.situ.trade.commons.domian.entity.Collect;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollectMapper {

    @Insert({
            "insert into collect",
            "(goods_id,user_id)",
            "values ",
            "(#{goodsId},#{userId})"
    })
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insert(Collect collect);

    @Delete({
            "delete from collect",
            "where id=#{id}",
    })
    int deleteById(Integer id);

    @Select({
            "select * from collect",
            "where id=#{id}",
    })
    Collect selectById(Integer id);

    @Select({
            "select * from collect",
            "where user_id=#{userId}",
    })
    Collect selectByUserId(Integer userId);

    @Select({
            "select * from collect",
            "where user_id=#{userId} and goods_id=#{goodsId}",
    })
    Collect selectByUserIdAndGoodsId(Integer userId, Integer goodsId);

    @Select({
            "<script>",
            "select * from collect",
            "<where>",
            "<if test='userId!=null'> and user_id=#{userId} </if>",
            "<if test='goodsId!=null'> and goods_id=#{goodsId} </if>",
            "</where>",
            "</script>"
    })
    List<Collect> select(Collect collect);
}
