package com.situ.trade.goodsservice.mapper;

import com.situ.trade.commons.domian.entity.GoodsPic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsPicMapper {

    @Select({
            "select * from goods_pic",
            "where goods_id=#{goodsId}",
    })
    List<GoodsPic> selectByGoodsId(Integer goodsId);

    @Select({
            "select * from goods_pic",
    })
    List<GoodsPic> select(GoodsPic goodsPic);

    @Insert({
            "insert into goods_pic",
            "(url,goods_id)",
            "value",
            "(#{url},#{goods_id})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insert(GoodsPic goodsPic);

    @Insert({
            "<script>",
            "insert into goods_pic",
            "(url,goods_id)",
            "values ",
            "<foreach collection='pics' item='pic' separator=','>",
            "(#{pic.url},#{goodsId})",
            "</foreach>",
            "</script>"
    })
    int insertBatch(List<GoodsPic> pics, Integer goodsId);

    @Update({
            "<script>",
            "update goods_pic",
            "<set>",
            "<if test='url!=null and url.length>0'>url=#{url},</if>",
            "<if test='goodsId!=null and goodsId.length>0'>goods_id=#{goodsId},</if>",
            "</set>",
            "where id=#{id}",
            "</script>"
    })
    int update(GoodsPic goodsPic);

    @Delete({
            "delete from goods_pic",
            "where goods_id=#{goodsId}",
    })
    int deleteByGoodsId(Integer goodsId);
}
