package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Pay;

import java.util.List;

public interface PayService {
    int add(Pay pay) throws Exception;

    int delete(Integer payId) throws Exception;

    int update(Pay pay) throws Exception;

    Pay selectById(Integer payId) throws Exception;

    List<Pay> select(Pay pay) throws Exception;

    PageInfo<Pay> searchForPage(Integer pageNum, Integer pageSize, Pay pay) throws Exception;
}
