package com.situ.trade.orderservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Carts;
import com.situ.trade.commons.domian.entity.Goods;
import com.situ.trade.commons.service.CartsService;
import com.situ.trade.commons.service.GoodsService;
import com.situ.trade.orderservice.mapper.CartsMapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartsServiceImpl implements CartsService {
    private final CartsMapper cartsMapper;
    //调用dubbo的服务
    @DubboReference(version = "1.0.0")
    private GoodsService goodsService;

    @Override
    public int add(Carts carts) throws Exception {
        int res;
        //查询购物车
        Carts sCart = cartsMapper.selectByUserIdAndGoodsId(carts.getUserId(),carts.getGoodsId());
        if(ObjectUtils.isEmpty(sCart)){
            //如果没有，则直接添加
            res = cartsMapper.insert(carts);
        }else {
            //如果已经有了，在原纪录上进行数量的累加,更新
            sCart.setQuantity(sCart.getQuantity()+1);
            sCart.setPrice(sCart.getPrice().add(carts.getPrice()));
            res=cartsMapper.update(sCart);
        }
        return res;
    }

    @Override
    public int delete(Integer cartId, Integer userId) throws Exception {
        Carts sCart = cartsMapper.selectByUserIdAndCartsId(userId,cartId);
        if(ObjectUtils.isEmpty(sCart)){
            throw new Exception("无效的购物车信息");
        }
        return cartsMapper.delete(cartId);
    }


    @Override
    public List<Carts> search(Carts carts) {
        //这里只能查询到购物车的信息
        List<Carts> carts1= cartsMapper.select(carts);
        //需要每一个商品的信息，需要调用goodsService，使用dubbo RPC基于长连接的
        for (Carts cart : carts1){
            cart.setGoods(goodsService.get(cart.getGoodsId()));
        }
        return carts1;
    }

    @Override
    public PageInfo<Carts> searchForPage(Integer pageNum, Integer pageSize, Carts carts) {
        PageHelper.startPage(pageNum,pageSize);
        List<Carts> list=cartsMapper.select(carts);
        return new PageInfo<>(list);
    }
}
