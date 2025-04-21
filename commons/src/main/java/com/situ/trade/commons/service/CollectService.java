package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Carts;
import com.situ.trade.commons.domian.entity.Collect;
import com.situ.trade.commons.domian.entity.User;

import java.util.List;

public interface CollectService {
    int add(Collect collect) throws Exception;

    int remove(Integer id, Integer userId)throws Exception;

    int update(Collect collect);

    List<Collect> search(Collect collect);

    PageInfo<Collect> searchForPage(Integer pageNum, Integer pageSize, Collect collect);


    Collect selectById(Integer id);

    Collect selectByUserId(Integer userId);

    Collect selectByUserIdAndGoodsId(Integer userId, Integer goodsId);


}
