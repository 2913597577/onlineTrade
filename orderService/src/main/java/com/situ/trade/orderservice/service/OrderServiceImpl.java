package com.situ.trade.orderservice.service;

import com.situ.trade.commons.domian.entity.Order;
import com.situ.trade.commons.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Override
    public int add(Order order) throws Exception {
        return 0;
    }

    @Override
    public int update(Order order) throws Exception {
        return 0;
    }

    @Override
    public int delete(Integer orderId) throws Exception {
        return 0;
    }
}
