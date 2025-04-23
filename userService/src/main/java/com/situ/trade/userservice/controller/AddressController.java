package com.situ.trade.userservice.controller;

import com.situ.trade.commons.domian.entity.Address;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/address")
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public Result add(@RequestBody Address address) {
        try {
            addressService.add(address);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @PutMapping
    public Result edit(@RequestBody Address address) {
        try {
            addressService.edit(address);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Integer id) {
        try {
            Address address = addressService.getById(id);
            return Result.success(address);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list/{userId}")
    public Result getByUserId(@PathVariable("userId") Integer userId) {
        try {
            List<Address> address = addressService.search(userId);
            return Result.success(address);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        try {
            addressService.delete(id);
            return Result.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }



}
