package com.situ.trade.userservice.mapper;

import com.situ.trade.commons.domian.entity.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressMapper {
    @Insert({
            "insert into address",
            "(address,user_id)",
            "values ",
            "(#{address},#{userId})"
    })
    int insert(Address addresses);
    @Delete({
            "delete from address",
            "where id =#{id}"
    })
    int delete(Integer id);
    @Update({
            "update address",
            "set address=#{address}",
            "where id =#{id}"
    })
    int update(Address address);
    @Select({
            "select * from address",
            "where user_id=#{userId}"
    })
    List<Address> select(Integer userId);

    @Select({
            "select * from address",
            "where id=#{id}"
    })
    Address selectById(Integer id);
}
