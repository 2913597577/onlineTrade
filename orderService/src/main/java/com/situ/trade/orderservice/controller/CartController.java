package com.situ.trade.orderservice.controller;


import com.situ.trade.commons.domian.entity.Carts;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.CartsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {
    private final CartsService cartsService;

    @PostMapping
    public Result add(@RequestBody Carts carts, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        carts.setUserId(user.getUserId());
        carts.setQuantity(1);
        try {
            cartsService.add(carts);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
    @DeleteMapping
    public Result delete(Integer castId) {
        try {
            cartsService.delete(castId);
            return Result.success();
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
            return Result.error(e.getMessage());
        }
    }
    @GetMapping
    public Result search(Integer pageNum, Integer pageSize,Carts carts,
                            Authentication authentication) {
        User user=(User) authentication.getPrincipal();
        carts.setUserId(user.getUserId());

        if(ObjectUtils.isEmpty(pageNum)){
            return Result.success(cartsService.search(carts));
        }else {
            return Result.success(cartsService.searchForPage(pageNum,pageSize,carts));
        }
    }
}
