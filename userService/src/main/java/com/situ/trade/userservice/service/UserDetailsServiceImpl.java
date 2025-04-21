package com.situ.trade.userservice.service;

import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 *  需要提供登录服务的应用，必须实现这个接口
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询数据库
        User user = userMapper.selectByUsername(username);
        //用户名不存在
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户不存在！");
        }
        //返回用户
        return user;
    }
}
