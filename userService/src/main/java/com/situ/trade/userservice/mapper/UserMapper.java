package com.situ.trade.userservice.mapper;

import com.situ.trade.commons.domian.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert({
            "insert into users",
            "(username,password)",
            "values ",
            "(#{username},#{password})",
    })
    @Options(useGeneratedKeys = true,keyColumn = "user_id",keyProperty = "userId")
    int insert(User user);

    /*@Insert({
            "insert into users",
            "(username,password,paypassword,realname,email,phone,address,image,idcard,salt)",
            "values ",
            "(#{username},#{password},#{password},#{realname},#{email},#{phone},#{address},#{image},#{idcard},#{salt})",
    })*/
    @Update({
            "<script>",
            "update users",
            "<set>",
            "<if test='password!=null and password.length>0'>password=#{password},</if>",
            "<if test='paypassword!=null and paypassword.length>0'>paypassword=#{paypassword},</if>",
            "<if test='phone!=null and phone.length>0'>phone=#{phone},</if>",
            "<if test='address!=null and address.length>0'>address=#{address},</if>",
            "<if test='email!=null and email.length>0'>email=#{email},</if>",
            "<if test='image!=null and image.length>0'>image=#{image},</if>",
            "<if test='status!=null'>status=#{status},</if>",
            "<if test='balance!=null'>balance=#{balance},</if>",
            "<if test='role!=null'>`role`=#{role},</if>",
            "<if test='storeId!=null'>store_id=#{storeId},</if>",
            "</set>",
            "where user_id=#{userId}",
            "</script>",
    })
    int update(User user);

    @Delete({
            "delete from users",
            "where user_id=#{userId}",
    })
    int delete(Integer userId);

    @Select({
            "select * from users",
            "where user_id=#{userId}",
    })
    User selectById(Integer userId);
    @Select({
            "select * from users",
            "where username=#{username}",
    })
    User selectByUsername(String username);
    @Select({
            "<script>",
            "select * from users",
            "<where>",
            "<if test='username!=null and username.length>0'> and username like concat('%',#{username},'%') </if>",
            "<if test='phone!=null and phone.length>0'> and phone like concat('%',#{phone},'%') </if>",
            "<if test='realname!=null and realname.length>0'> and realname like concat('%',#{realname},'%') </if>",
            "<if test='status!=null'> and status=#{status} </if>",
            "<if test='role!=null'> and `role`=#{role} </if>",
            "</where>",
            "order by create_time desc",
            "</script>",
    })
    List<User> select(User user);
}
