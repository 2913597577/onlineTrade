package com.situ.trade.goodsservice.mapper;

import com.situ.trade.commons.domian.entity.Goods;
import com.situ.trade.commons.domian.entity.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Insert({
            "insert into goods",
            "(name,price,stock,description,type_id,detail,markPrice,purchasePrice,color,version,recom,store_id,after_sale_period,after_sale_service)",
            "values ",
            "(#{name},#{price},#{stock},#{description},#{typeId},#{detail},#{markPrice},#{purchasePrice},#{color},#{version},#{recom},#{storeId},#{afterSalePeriod},#{afterSaleService})",
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
            "<if test='sold!=null'>sold=#{sold},</if>",
            "<if test='afterSalePeriod!=null'>after_sale_period=#{afterSalePeriod},</if>",
            "<if test='afterSaleService!=null and detail.length>0'>after_sale_service=#{afterSaleService},</if>",
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
            "select goods.*, type.type_id as type_id",
            "from goods",
            "left join type on goods.type_id = type.type_id",
            "where goods.goods_id = #{goodsId}"
    })
    @Results({
            @Result(property = "goodsId", column = "goods_id", id = true),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type_id",
                    many = @Many(select = "com.situ.trade.goodsservice.mapper.TypeMapper.searchByTypeId")),
            @Result(property = "pics", column = "goods_id",
                    many = @Many(select = "com.situ.trade.goodsservice.mapper.GoodsPicMapper.selectByGoodsId"))
    })
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
            "<if test='storeId!=null'> and goods.store_id=#{storeId} </if>",
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
