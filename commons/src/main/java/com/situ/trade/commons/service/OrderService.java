package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Carts;
import com.situ.trade.commons.domian.entity.Order;
import com.situ.trade.commons.domian.vo.Result;

import java.util.List;

public interface OrderService {

    Result add(Order order) throws Exception;

    int update(Order order) throws Exception;

    int delete(Integer orderId) throws Exception;

    Order selectById(Integer orderId) throws Exception;

    List<Order> select(Order order) throws Exception;

    PageInfo<Order> searchForPage(Integer pageNum, Integer pageSize, Order order) throws Exception;

}
