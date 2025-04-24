package com.situ.trade.goodsservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Type;
import com.situ.trade.commons.service.TypeService;
import com.situ.trade.goodsservice.mapper.TypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeMapper typeMapper;

    @Override
    public int add(Type type) throws Exception {
       List<Type> sType=typeMapper.selectByName(type.getTypename());
       if(!ObjectUtils.isEmpty(sType)){
           throw new Exception("分类已存在!");
       }
       return typeMapper.insert(type);
    }

    @Override
    public int update(Type type) throws Exception {
        return typeMapper.update(type);
    }

    @Override
    public Type searchById(Integer id) {
        return typeMapper.searchByTypeId(id);
    }

    @Override
    public List<Type> search(Type type) {
        return typeMapper.search(type);
    }

    @Override
    public PageInfo<Type> searchForPage( Integer pageNum, Integer pageSize,Type type) {
        PageHelper.startPage(pageNum, pageSize);
        List<Type> list = typeMapper.search(type);
        return new PageInfo<>(list);
    }
}
