package com.situ.trade.commons.service;

import com.situ.trade.commons.domian.entity.Order;

public interface OrderService {

    int add(Order order) throws Exception;

    int update(Order order) throws Exception;

    int delete(Integer orderId) throws Exception;


}
