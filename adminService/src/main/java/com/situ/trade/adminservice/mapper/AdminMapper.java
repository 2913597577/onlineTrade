package com.situ.trade.adminservice.mapper;

import com.situ.trade.commons.domian.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    //操作
    @Insert({
            "insert into staffs",
            "(username,name,password,salt,age,sex,phone,idcard,address)",
            "values ",
            "(#{username},#{name},#{password},#{salt},#{age},#{sex},#{phone},#{idcard},#{address})"
    })
    //自增主键
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insert(Admin admin);
    //更新  不能直接使用动态SQL,必须使用<script>把SQL包裹起来
    @Update({
            "<script>",
            "update staffs",
            "<set>",
            "<if test='password!=null and password.length>0'> password=#{password},</if>",
            "<if test='phone!=null and phone.length>0'> phone=#{phone},</if>",
            "<if test='salt!=null and salt.length>0'> salt=#{salt},</if>",
            "<if test='name!=null and name.length>0'> name=#{name},</if>",
            "<if test='status!=null'> status=#{status}</if>",
            "</set>",
            "where id=#{id}",
            "</script>",
    })
    int update(Admin admin);
    //查询
    @Select({
            "select * from staffs",
            "where id=#{id}",
    })
    Admin selectById(Integer id);
    //根据用户名查询
    @Select({
            "select * from staffs",
            "where username=#{username}",
    })
    Admin selectByUsername(String username);
    //多条件组合查询
    @Select({
            "<script>",
            "select * from staffs",
            "<where>",
            "<if test='username!=null and username.length>0'> and username like concat('%',#{username},'%') </if>",
            "<if test='phone!=null and phone.length>0'> and phone like concat('%',#{phone},'%') </if>",
            "<if test='name!=null and name.length>0'> and name like concat('%',#{name},'%') </if>",
            "<if test='status!=null'> and status=#{status} </if>",
            "</where>",
            "order by startdate desc",
            "</script>",
    })
    List<Admin> select(Admin admin);

}
