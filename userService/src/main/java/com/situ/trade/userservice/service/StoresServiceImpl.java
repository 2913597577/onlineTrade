package com.situ.trade.userservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Stores;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.StoresService;
import com.situ.trade.commons.service.UserService;
import com.situ.trade.userservice.mapper.StoresMapper;
import com.situ.trade.userservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@DubboService(interfaceClass = StoresService.class,version = "1.0.0")
public class StoresServiceImpl implements StoresService {
    private final StoresMapper storesMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Result add(Stores stores) throws Exception {
        User user = new User();
        user.setUserId(stores.getUserId());
        user.setRole(1);
        try {
            storesMapper.insert(stores);
            Integer storeId = stores.getId();
            user.setStoreId(storeId);
            userMapper.update(user);
            Stores store = storesMapper.selectById(storeId);
            return Result.success(store);
        }catch (Exception e){
            throw new Exception("申请失败，请稍后再试");
        }
    }

    @Override
    public int edit(Stores stores) throws Exception {
        return storesMapper.update(stores);
    }

    @Override
    public Stores getById(Integer id){
        return storesMapper.selectById(id);
    }

    @Override
    public Stores searchByUserId(Integer userId){
        return storesMapper.selectByUserId(userId);
    }

    @Override
    public PageInfo<Stores> searchForPage(Integer pageNum, Integer pageSize, Stores stores){

        PageHelper.startPage(pageNum, pageSize);
        List<Stores> list = storesMapper.select(stores);
        return new PageInfo<>(list);
    }
}
