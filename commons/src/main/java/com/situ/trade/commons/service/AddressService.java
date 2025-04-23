package com.situ.trade.commons.service;

import com.situ.trade.commons.domian.entity.Address;

import java.util.List;

public interface AddressService {

    int add(Address address) throws Exception;

    int edit(Address address) throws Exception;

    Address getById(Integer id);

    List<Address> search(Integer userId);

    int delete (Integer id) throws Exception;

}
