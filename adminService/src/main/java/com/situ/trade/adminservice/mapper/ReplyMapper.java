package com.situ.trade.adminservice.mapper;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Reply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReplyMapper {

    @Insert({
            "insert into reply",
            "(comment_id),(reply),(staff_id)",
            "values ",
            "(#{commentId},#{reply},#{staffId})"
    })
    int insert (Reply reply);

    @Delete({
            "delete from reply",
            "where id=#{id}"
    })
    int delete (Integer id);

    @Select({
            "select * from reply"
    })
    List<Reply> select(Reply reply);
}
