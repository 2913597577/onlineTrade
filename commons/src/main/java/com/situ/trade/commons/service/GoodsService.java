package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Goods;

import java.util.List;

public interface GoodsService {
    int add(Goods goods) throws Exception;

    int update(Goods goods) throws Exception;

    int delete(Integer goodsId) throws Exception;

    Goods get(Integer goodsId);

    List<Goods> search(Goods goods);

    PageInfo<Goods> searchForPage(Integer pageNum, Integer pageSize,Goods goods);


}
