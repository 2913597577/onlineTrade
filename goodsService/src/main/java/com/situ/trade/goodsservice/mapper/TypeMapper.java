package com.situ.trade.goodsservice.mapper;

import com.situ.trade.commons.domian.entity.Type;
import net.sf.jsqlparser.schema.Column;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TypeMapper {

    @Select({
            "<script>",
            "select * from type",
            "<where>",
            "<if test='typeId!=null'> and type_id=#{typeId}</if>",
            "</where>",
            "</script>",
    })
    Type searchByTypeId(Integer typeId);

    @Select({
            "select * from type",
            "where parent_id=#{parentId} and status=0"
    })
    List<Type> selectByParentId(Integer parentId);

    @Select({
            "<script>",
            "select * from type",
            "<where>",
            "<if test='parentId!=null'> and parent_id=#{parentId}</if>",
            "<if test='dscp!=null and dscp.length>0'> and dscp like concat('%',#{dscp},'%')</if>",
            "<if test='typename!=null and typename.length>0'> and typename like concat('%',#{typename},'%')</if>",
            "<if test='status!=null'> and status=#{status}</if>",
            "<if test='recom!=null'> and recom=#{recom}</if>",
            "</where>",
            "</script>",
    })
    @Results({   //相当于 resultMap
            @Result(column ="type_id",property = "typeId"),
            @Result(column ="type_id",property = "goods",
                    many=@Many(select = "com.situ.trade.goodsservice.mapper.GoodsMapper.getByTypeId")),
            @Result(column = "type_id",property = "children",
                    many = @Many(select = "selectByParentId")),
            @Result(column = "parent_id",property = "parentId"),
            @Result(column = "parent_id",property = "parent",
                    one = @One(select = "selectById"))

    })
    List<Type> search(Type type);

    @Select({
            "select * from type",
            "where type_id =#{id}"
    })
    Type selectById(Integer id);
    @Select({
            "select * from type",
            "where typename =#{name}"
    })
    List<Type> selectByName(String name);
    @Insert({
            "insert into type",
            "(typename,parent_id,dscp,pic,recom)",
            "values ",
            "(#{typename},#{parentId},#{dscp},#{pic},#{recom})"
    })
    int insert(Type type);

    @Update({
            "<script>",
            "update type",
            "<set>",
            "<if test='parentId!=null'>parent_id=#{parentId},</if>",
            "<if test='dscp!=null and dscp.length>0'>dscp=#{dscp},</if>",
            "<if test='typename!=null and typename.length>0'>typename=#{typename},</if>",
            "<if test='pic!=null and pic.length>0'>pic=#{pic},</if>",
            "<if test='status!=null'>status=#{status},</if>",
            "<if test='recom!=null'>recom=#{recom},</if>",
            "</set>",
            "where type_id =#{typeId}",
            "</script>"
    })
    int update(Type type);



}
