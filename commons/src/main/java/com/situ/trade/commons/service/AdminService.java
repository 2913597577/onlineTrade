package com.situ.trade.commons.service;


import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Admin;

import java.util.List;

public interface AdminService {
    //添加
    int add(Admin admin) throws Exception;

    //修改
    int edit(Admin admin) throws Exception;

    //查询
    Admin getById(Integer id);

    //多条件组合查询
    List<Admin> search(Admin admin);

    //分页查询 PageHelper
    PageInfo<Admin> searchForPage(Integer pageNum, Integer pageSize, Admin admin);

    Admin login(Admin admin) throws Exception;
}
