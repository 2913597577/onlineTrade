package com.situ.trade.goodsservice.mapper;

import com.situ.trade.commons.domian.entity.Goods;
import com.situ.trade.commons.domian.entity.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Insert({
            "insert into goods",
            "(name,price,stock,description,type_id,detail,markPrice,purchasePrice,color,version,recom)",
            "values ",
            "(#{name},#{price},#{stock},#{description},#{typeId},#{detail},#{markPrice},#{purchasePrice},#{color},#{version},#{recom})",
    })
    @Options(useGeneratedKeys = true,keyColumn = "goods_id",keyProperty = "goodsId")
    int insert(Goods goods);
    @Update({
            "<script>",
            "update goods",
            "<set>",
            "<if test='name!=null and name.length>0'>name=#{name},</if>",
            "<if test='price!=null'>price=#{price},</if>",
            "<if test='markPrice!=null'>markPrice=#{markPrice},</if>",
            "<if test='purchasePrice!=null'>purchasePrice=#{purchasePrice},</if>",
            "<if test='stock!=null'>stock=#{stock},</if>",
            "<if test='description!=null and description.length>0'>description=#{description},</if>",
            "<if test='detail!=null and detail.length>0'>detail=#{detail},</if>",
            "<if test='status!=null'>status=#{status},</if>",
            "<if test='recom!=null'>recom=#{recom},</if>",
            "<if test='score!=null'>score=#{score},</if>",
            "</set>",
            "where goods_id=#{goodsId}",
            "</script>"
    })
    int update(Goods goods);

    @Delete({
            "delete from goods",
            "where goods_id=#{goodsId}",
    })
    int delete(Integer goodsId);

    @Select({
            "select * from goods",
            "where goods_id=#{goodsId}",
    })
    @ResultMap("goods")  //在引用id=goods的那套@Results
    Goods getById(Integer goodsId);

    @Select({
            "select goods.* from goods",
            "inner join type on goods.type_id=type.type_id",
            "where goods.type_id=#{typeId} or type.parent_id=#{typeId}",
    })
    @Results(id = "goods", value = {
            @Result(column = "goods_id",property ="goodsId",id = true ),
            @Result(column = "goods_id",property = "pics",
                    many = @Many(select = "com.situ.trade.goodsservice.mapper.GoodsPicMapper.selectByGoodsId")
            )
    })
    List<Goods> getByTypeId(Integer typeId);

    @Select({
            "<script>",
            "select goods.* from goods",
            "inner join type on goods.type_id=type.type_id",
            "<where>",
            "<if test='status!=null'> and goods.status=#{status} </if>",
            "<if test='name!=null and name.length>0'> and name like concat('%',#{name},'%')</if>",
            "<if test='recom!=null'> and goods.recom=#{recom} </if>",
            "<if test='typeId!=null'> and (goods.type_id=#{typeId} or type.parent_id=#{typeId}) </if>",
            "</where>",
            "</script>",
    })
    @Results({
            @Result(property = "typeId",column = "type_id"),
            @Result(column = "goods_id",property ="goodsId",id = true ),
            @Result(property = "type",column = "type_id",
            many = @Many(select = "com.situ.trade.goodsservice.mapper.TypeMapper.searchByTypeId")),
            @Result(column = "goods_id",property = "pics",
                    many = @Many(select = "com.situ.trade.goodsservice.mapper.GoodsPicMapper.selectByGoodsId")
            )
    })
    List<Goods> select(Goods goods);


}
