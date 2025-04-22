package com.situ.trade.userservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.UserVo;
import com.situ.trade.commons.service.UserService;
import com.situ.trade.userservice.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    //注入一个认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;
    //注入一个加密器
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public int add(UserVo user) throws Exception {
        //验证两次密码是否一致
        if(!user.getPassword().equals(user.getRepassword())){
            throw new Exception("两次密码不一致！");
        }
        //验证用户名是否可用
        User sUser = userMapper.selectByUsername(user.getUsername());
        if(!ObjectUtils.isEmpty(sUser)){
            throw new Exception("用户名已存在！");
        }
        //对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //保存到数据库
        return userMapper.insert(user);
}

    @Override
    public User login(User user) throws Exception{
        //使用Security进行登录
        //创建一个令牌
        UsernamePasswordAuthenticationToken token=
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        try {
            //验证账号是否合法
            //验证令牌是不是一个有效的令牌 1-根据用户名获取用户   2-验证密码
            Authentication authentication= authenticationManager.authenticate(token);

            //得到用户信息
            User loginUser=(User)authentication.getPrincipal();
            return loginUser;
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("用户名或密码错误！");
        }
    }

    @Override
    public int update(User user) throws Exception {
        return userMapper.update(user);
    }

    @Override
    public int delete(Integer userId) throws Exception {
        return userMapper.delete(userId);
    }

    @Override
    public User getById(Integer userId) {
        return userMapper.selectById(userId);
    }
    @Override
    public List<User> search(User user) {
        return userMapper.select(user);
    }

    @Override
    public PageInfo<User> searchForPage(Integer pageNum, Integer pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.select(user);
        return new PageInfo<>(list);
    }
}
