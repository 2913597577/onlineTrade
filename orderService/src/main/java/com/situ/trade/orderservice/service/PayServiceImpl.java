package com.situ.trade.orderservice.service;


import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Goods;
import com.situ.trade.commons.domian.entity.Order;
import com.situ.trade.commons.domian.entity.Pay;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.GoodsService;
import com.situ.trade.commons.service.OrderService;
import com.situ.trade.commons.service.PayService;
import com.situ.trade.commons.service.UserService;
import com.situ.trade.orderservice.mapper.OrderMapper;
import com.situ.trade.orderservice.mapper.PayMapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {
    private final PayMapper payMapper;
    private final OrderService orderService;

    //调用dubbo的服务
    @DubboReference(version = "1.0.0")
    private GoodsService goodsService;
    @DubboReference(version = "1.0.0")
    private UserService userService;

    @Override
    public int add(Pay pay) throws Exception {
        Order order = orderService.selectById(pay.getOrderId());
        BigDecimal amount = new BigDecimal(order.getAmount());
        // 计算总金额
        BigDecimal totalAmount = amount.multiply(order.getGoods().getPurchasePrice());
        Goods goods = goodsService.get(order.getGoodsId());
        if(goods!=null){
            if(goods.getStatus()==1){
                throw new Exception("商品已下架");
            }
            if(goods.getStock()<=order.getAmount()){
                throw new Exception ("库存不足，仅剩"+goods.getStock()+"件");
            }
        }

            if(pay.getPayType()==0){
                User user = userService.getById(pay.getUserId());
                // 检查用户余额是否足够
                if (user.getBalance().compareTo(totalAmount) >= 0) {
                    // 用户余额足够，进行支付操作
                    user.setBalance(user.getBalance().subtract(totalAmount));
                    userService.update(user);
                }else {
                    throw new Exception("用户余额不足，请充值");
                }
            }
            int result = payMapper.insert(pay);
            if(result==0){
                throw new Exception("支付失败");
            }
        order.setStatus(1);
        orderService.update(order);
        return result;
    }

    @Override
    public int delete(Integer payId) throws Exception {
        return 0;
    }

    @Override
    public int update(Pay pay) throws Exception {
        return 0;
    }

    @Override
    public Pay selectById(Integer payId) throws Exception {
        return null;
    }

    @Override
    public List<Pay> select(Pay pay) throws Exception {
        return payMapper.select(pay);
    }

    @Override
    public PageInfo<Pay> searchForPage(Integer pageNum, Integer pageSize, Pay pay) throws Exception {
        return null;
    }
}
