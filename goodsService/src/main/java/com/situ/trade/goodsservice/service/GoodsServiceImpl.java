package com.situ.trade.goodsservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Goods;
import com.situ.trade.commons.service.GoodsService;
import com.situ.trade.goodsservice.mapper.GoodsMapper;
import com.situ.trade.goodsservice.mapper.GoodsPicMapper;
import com.situ.trade.goodsservice.mapper.TypeMapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@DubboService(interfaceClass = GoodsService.class,version = "1.0.0")
public class GoodsServiceImpl implements GoodsService {
    private final GoodsMapper goodsMapper;
    private final GoodsPicMapper goodsPicMapper;

    @Override
    @Transactional(rollbackFor = Exception.class,
            isolation = Isolation.READ_UNCOMMITTED)
    //基于AOP
    public int add(Goods goods) throws Exception {
        //插入商品信息
        int res=goodsMapper.insert(goods);
        //插入图片信息
        if(!ObjectUtils.isEmpty(goods.getPics())){
            res=goodsPicMapper.insertBatch(goods.getPics(),goods.getGoodsId());
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,
            isolation = Isolation.READ_COMMITTED)
    public int update(Goods goods) throws Exception {
        int res=goodsMapper.update(goods);
        //重置图片
        if(!ObjectUtils.isEmpty(goods.getPics())){
            //先删除老的图片
            res=goodsPicMapper.deleteByGoodsId(goods.getGoodsId());
            //再插入新的图片
            res=goodsPicMapper.insertBatch(goods.getPics(),goods.getGoodsId());
        }
        return res;
    }

    @Override
    public int delete(Integer goodsId) throws Exception {
        return goodsMapper.delete(goodsId);
    }

    @Override
    public Goods get(Integer goodsId) {
        return goodsMapper.getById(goodsId);
    }

    @Override
    public List<Goods> search(Goods goods) {
        return goodsMapper.select(goods);
    }

    @Override
    public PageInfo<Goods> searchForPage(Integer pageNum, Integer pageSize, Goods goods) {
        PageHelper.startPage(pageNum,pageSize);
        List<Goods> list = goodsMapper.select(goods);
        return new PageInfo<>(list);
    }
}
