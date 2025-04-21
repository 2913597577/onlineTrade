package com.situ.trade.shipservice.mapper;

import com.situ.trade.commons.domian.entity.Ship;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShipMapper {
    @Insert({
            "insert into ship",
            "(order_id,company,address)",
            "values ",
            "(#{orderId},#{company},#{address})"
    })
    int insert(Ship ship);
    @Update({
            "update ship",
            "set createtime=NOW()",
            "where order_id=#{orderId}"
    })
    int update(Ship ship);
    @Delete({
            "delete from ship",
            "where order_id=#{orderId}"
    })
    int delete(Integer orderId);
    @Select({
            "select * from ship"
    })
    List<Ship> select(Ship ship);
}
