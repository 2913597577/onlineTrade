package com.situ.trade.userservice.mapper;

import com.situ.trade.commons.domian.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert({
            "insert into comment",
            "(content,order_id,user_id)",
            "values ",
            "(#{content},#{orderId},#{userId})"
    })
    int insert(Comment comment);

    @Delete({
            "delete from comment",
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
