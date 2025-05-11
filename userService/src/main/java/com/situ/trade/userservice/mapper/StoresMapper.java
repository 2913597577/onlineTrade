package com.situ.trade.userservice.mapper;

import com.situ.trade.commons.domian.entity.Stores;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoresMapper {

    /**
     * 插入店铺信息
     */
    @Insert({
            "insert into stores",
            "(store_name, user_id, address,ad)",
            "values",
            "(#{storeName}, #{userId}, #{address}, #{ad})"
    })
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insert(Stores store);

    /**
     * 根据主键删除店铺
     */
    @Delete({
            "delete from stores",
            "where id = #{id}"
    })
    int delete(Integer id);

    /**
     * 更新店铺信息
     */
    @Update({
            "<script>",
            "update stores",
            "<set>",
            "   <if test='storeName != null'>store_name = #{storeName},</if>",
            "   <if test='userId != null'>user_id = #{userId},</if>",
            "   <if test='address != null'>address = #{address},</if>",
            "   <if test='status != null'>status = #{status},</if>",
            "   <if test='score != null'>score = #{score},</if>",
            "   <if test='ad != null'>ad = #{ad},</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    int update(Stores store);

    /**
     * 根据用户ID查询店铺列表
     */
    @Select({
            "select * from stores",
            "where user_id = #{userId}"
    })
    Stores selectByUserId(Integer userId);

    /**
     * 根据店铺ID查询店铺详情
     */
    @Select({
            "select * from stores",
            "where id = #{id}"
    })
    Stores selectById(Integer id);

    @Select({
            "<script>",
            "select * from stores",
            "<where>",
            "   <if test='stores.storeName != null and stores.storeName != \"\"'>",
            "       and store_name like CONCAT('%', #{stores.storeName}, '%')",
            "   </if>",
            "   <if test='stores.userId != null'>",
            "       and user_id = #{stores.userId}",
            "   </if>",
            "   <if test='stores.status != null'>",
            "       and status = #{stores.status}",
            "   </if>",
            "   <if test='stores.address != null and stores.address != \"\"'>",
            "       and address like CONCAT('%', #{stores.address}, '%')",
            "   </if>",
            "   <if test='stores.score != null'>",
            "       and score >= #{stores.score}",
            "   </if>",
            "   <if test='stores.createTime != null'>",
            "       and createTime >= #{stores.createTime}",
            "   </if>",
            "</where>",
            "</script>"
    })
    List<Stores> select(@Param("stores") Stores stores);



}
