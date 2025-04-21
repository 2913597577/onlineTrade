package com.situ.trade.adminservice.service;

import com.situ.trade.adminservice.mapper.AdminMapper;
import com.situ.trade.commons.domian.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AdminMapper adminMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin=adminMapper.selectByUsername(username);
        if(ObjectUtils.isEmpty(admin)){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return admin;
    }
}
