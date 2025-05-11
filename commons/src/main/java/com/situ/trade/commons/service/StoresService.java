package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Stores;
import com.situ.trade.commons.domian.vo.Result;

import java.util.List;

public interface StoresService {

    Result add(Stores stores) throws Exception;

    int edit(Stores stores) throws Exception;

    Stores getById(Integer id);

    Stores searchByUserId(Integer userId);

    PageInfo<Stores> searchForPage(Integer pageNum, Integer pageSize, Stores stores);


}
