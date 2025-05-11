package com.situ.trade.adminservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.adminservice.mapper.AdminMapper;
import com.situ.trade.commons.domian.entity.Admin;
import com.situ.trade.commons.service.AdminService;
import com.situ.trade.commons.util.MD5Util;
import lombok.NoArgsConstructor;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    //注入Mapper
    private  final AdminMapper adminMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int add(Admin admin) throws Exception {
        //账号不可重复
        Admin sAdmin=adminMapper.selectByUsername(admin.getUsername());
        if(!ObjectUtils.isEmpty(sAdmin)){
            throw new Exception("用户名不可用！");
        }
        //生成八位的盐
        String salt = UUID.randomUUID().toString().substring(0,8);
        admin.setSalt(salt);

        //加密密码
        admin.setPassword(MD5Util.getDBMD5(admin.getPassword(),salt));

        //保存到数据库
        return adminMapper.insert(admin);
    }

    @Override
    public int edit(Admin admin) throws Exception {
        Admin sAdmin=adminMapper.selectById(admin.getId());
        if(ObjectUtils.isEmpty(sAdmin)){
            throw new Exception("无效的ID");
        }

        //如果修改的是密码
        if(ObjectUtils.isEmpty(admin.getPassword())){
            //再随机一个盐，重新加密
            admin.setSalt(UUID.randomUUID().toString().substring(0,8));
            admin.setPassword(MD5Util.getDBMD5(admin.getPassword(),admin.getSalt()));
        }

        //写入数据库
        return adminMapper.update(admin);
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.selectById(id);
    }

    @Override
    public List<Admin> search(Admin admin) {
        return adminMapper.select(admin);
    }

    @Override
    public PageInfo<Admin> searchForPage(Integer pageNum, Integer pageSize, Admin admin) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = adminMapper.select(admin);
        return new PageInfo<>(admins);
    }

    @Override
    public Admin login(Admin admin) throws Exception {
        //加密密码
        //System.out.println(passwordEncoder.encode(admin.getPassword()));

        UsernamePasswordAuthenticationToken auth
                = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(auth);
            admin=(Admin)authentication.getPrincipal();
            admin.setPermission(true);
            return admin;
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("用户名或密码错误！");
        }
    }
}
