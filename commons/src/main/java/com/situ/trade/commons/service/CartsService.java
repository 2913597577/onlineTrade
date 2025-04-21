package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Carts;

import java.util.List;

public interface CartsService {

    int add(Carts carts) throws Exception;

    int delete(Integer cartId) throws Exception;

    List<Carts> search(Carts carts);

    PageInfo<Carts> searchForPage(Integer pageNum,Integer pageSize, Carts carts);
}
