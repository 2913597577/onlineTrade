package com.situ.trade.userservice.mapper;

import com.situ.trade.commons.domian.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert({
            "insert into comment",
            "(content,order_id,user_id,goods_score,store_score)",
            "values ",
            "(#{content},#{orderId},#{userId},#{goodsScore},#{storeScore})"
    })
    int insert(Comment comment);

    @Update({
            "update comment set is_del = #{isDel}",
            "where id =#{id}"
    })
    int delete(Integer id);
    @Select({
            "<script>",
            "select * from comment",
            "<where>",
            "<if test='content!=null and content.length>0'> and content like concat('%',#{content},'%') </if>",
            "<if test='userId!=null'> and user_id=#{userId} </if>",
            "</where>",
            "order by createtime desc",
            "</script>",

    })
    List<Comment> select(Comment comment);

}
