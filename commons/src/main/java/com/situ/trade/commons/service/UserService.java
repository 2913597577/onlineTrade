package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.UserVo;

import java.util.List;

public interface UserService {
    int add (UserVo user) throws Exception;

    int update (User user) throws Exception;

    int delete (Integer userId) throws Exception;

    User getById (Integer userId);

    User login(User user) throws Exception;

    List<User> search(User user);

    PageInfo<User> searchForPage(Integer pageNum, Integer pageSize,User user);




}
