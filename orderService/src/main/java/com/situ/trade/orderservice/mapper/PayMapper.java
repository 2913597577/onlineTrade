package com.situ.trade.orderservice.mapper;

import com.situ.trade.commons.domian.entity.Pay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PayMapper {

    @Insert({
            "insert into pay",
            "(reder_id,user_id)"
    })
    int insert(Pay pay);

    @Select({
            "select * from pay",
            "where user_id=#{userId}"
    })
    List<Pay> select(Pay pay);
}
