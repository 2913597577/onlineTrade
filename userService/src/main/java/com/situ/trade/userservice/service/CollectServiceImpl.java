package com.situ.trade.userservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Collect;
import com.situ.trade.commons.service.CollectService;
import com.situ.trade.commons.service.GoodsService;
import com.situ.trade.userservice.mapper.CollectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectServiceImpl implements CollectService {

    private final CollectMapper collectMapper;

    @DubboReference(version = "1.0.0")
    private GoodsService goodsService;

    @Override
    public int add(Collect collect) throws Exception {
        //如果数据库中，已经存在了一条一样的收藏信息，这里就不能重复收藏了
         Collect sCollect=collectMapper.selectByUserIdAndGoodsId(
                 collect.getUserId(),collect.getGoodsId());
         if(!ObjectUtils.isEmpty(sCollect)){
             throw new Exception("请不要重复收藏");
         }
        return collectMapper.insert(collect);
    }

    @Override
    public int remove(Integer id, Integer userId) throws Exception {
        //先验证
        Collect collect=collectMapper.selectById(id);
        if(ObjectUtils.isEmpty(collect)){
            throw new Exception("无效的收藏信息");
        }
        if(!collect.getUserId().equals(userId)){
            throw new Exception("用户信息错误！");
        }
        //从数据库中删除
        return collectMapper.deleteById(id);
    }

    @Override
    public int update(Collect collect) {
        return 0;
    }

    @Override
    public List<Collect> search(Collect collect) {
        return collectMapper.select(collect);
    }

    @Override
    public PageInfo<Collect> searchForPage(Integer pageNum, Integer pageSize, Collect collect) {
        PageHelper.startPage(pageNum, pageSize);
        List<Collect> list = collectMapper.select(collect);
        return new PageInfo<>(list);
    }

    @Override
    public Collect selectById(Integer id) {
        return null;
    }

    @Override
    public List<Collect> selectByUserId(Integer userId) {
        List<Collect> list = collectMapper.selectByUserId(userId);
        list.forEach(collect -> {
            collect.setGoods(goodsService.get(collect.getGoodsId()));
        });
        return list;
    }

    @Override
    public Collect selectByUserIdAndGoodsId(Integer userId, Integer goodsId) {
        return collectMapper.selectByUserIdAndGoodsId(userId, goodsId);
    }
}
