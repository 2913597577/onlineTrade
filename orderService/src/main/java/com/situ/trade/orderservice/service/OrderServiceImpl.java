package com.situ.trade.orderservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Carts;
import com.situ.trade.commons.domian.entity.Goods;
import com.situ.trade.commons.domian.entity.Order;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.GoodsService;
import com.situ.trade.commons.service.OrderService;
import com.situ.trade.commons.service.UserService;
import com.situ.trade.orderservice.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    //调用dubbo的服务
    @DubboReference(version = "1.0.0")
    private GoodsService goodsService;
    @DubboReference(version = "1.0.0")
    private UserService userService;

    @Override
    @Transactional
    public Result add(Order order) throws Exception {
        if(order!=null){
           Goods goods= goodsService.get(order.getGoodsId());
           if(goods!=null){
               if(goods.getStock()>=order.getAmount()){
                   goods.setStock(goods.getStock()-order.getAmount());
                   goodsService.update(goods);
                   orderMapper.insert(order);
                   return Result.success(order);
               }else {
                   return Result.error("库存不足，仅剩"+goods.getStock()+"件");
               }
           }
        }
        return Result.error("下单失败");
    }

    @Override
    public int update(Order order) throws Exception {
        return orderMapper.update(order);
    }

    @Override
    public int delete(Integer orderId) throws Exception {
        return orderMapper.delete(orderId);
    }
    @Override
    public Order selectById(Integer orderId) throws Exception {
        Order order = orderMapper.selectById(orderId);
        if(order!=null){
            order.setGoods(goodsService.get(order.getGoodsId()));
            order.setUser(userService.getById(order.getUserId()));
        }
        return order;
    }
    @Override
    public List<Order> select(Order order) throws Exception {
        List<Order> list=orderMapper.select(order);
        if(list!=null||list.size()!=0){
            list.forEach(item->{
                item.setGoods(goodsService.get(item.getGoodsId()));
                item.setUser(userService.getById(item.getUserId()));
            });
        }
        return list;
    }

    @Override
    public PageInfo<Order> searchForPage(Integer pageNum, Integer pageSize, Order order) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> list=orderMapper.select(order);
        if(list!=null||list.size()!=0){
            list.forEach(item->{
                item.setGoods(goodsService.get(item.getGoodsId()));
                item.setUser(userService.getById(item.getUserId()));
            });
        }
        return new PageInfo<>(list);
    }
}
