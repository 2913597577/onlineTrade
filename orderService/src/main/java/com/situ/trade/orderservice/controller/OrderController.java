package com.situ.trade.orderservice.controller;

import com.situ.trade.commons.domian.entity.Order;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Result add(@RequestBody Order order, Authentication authentication) {
        try {
            return orderService.add(order);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id, Authentication authentication) {
        try {
            orderService.delete(id);
            return Result.success();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping
    public Result search(Integer pageNum, Integer pageSize, Order order, Authentication authentication) {
        try {
        if (ObjectUtils.isEmpty(pageNum)) {
            User user=(User) authentication.getPrincipal();
            order.setUserId(user.getUserId());
            List<Order> orderList = orderService.select(order);
            return Result.success(orderList);
        } else {
            return Result.success(orderService.searchForPage(pageNum, pageSize, order));
        }
        }catch (Exception e){
            log.warn(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
    @PutMapping
    public Result update(@RequestBody Order order) {
        try {
            orderService.update(order);
            return Result.success();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public Result selectById(@PathVariable("id") Integer id) {
        try {
            Order order = orderService.selectById(id);
            return Result.success(order);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
