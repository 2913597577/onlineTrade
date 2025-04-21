package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Type;

import java.util.List;

public interface TypeService {

    int add(Type type) throws Exception;

    int update(Type type) throws Exception;

    Type searchById(Integer id);

    List<Type> search(Type type);

    PageInfo<Type> searchForPage( Integer pageNum, Integer pageSize,Type type);
}
