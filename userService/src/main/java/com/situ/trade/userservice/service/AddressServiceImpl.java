package com.situ.trade.userservice.service;

import com.situ.trade.commons.domian.entity.Address;
import com.situ.trade.commons.service.AddressService;
import com.situ.trade.userservice.mapper.AddressMapper;
import com.situ.trade.userservice.mapper.CollectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;
    @Override
    public int add(Address address) throws Exception {
        return  addressMapper.insert(address);
    }

    @Override
    public int edit(Address address) throws Exception {
        return addressMapper.update(address);
    }

    @Override
    public Address getById(Integer id) {
        return addressMapper.selectById(id);
    }

    @Override
    public List<Address> search(Integer userId) {
        return addressMapper.select(userId);
    }

    @Override
    public int delete(Integer id) throws Exception {
        return addressMapper.delete(id);
    }

}
