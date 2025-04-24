package com.situ.trade.orderservice.controller;

import com.situ.trade.commons.domian.entity.Pay;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pay")
@Slf4j
public class PayController {

    private final PayService payService;

    @PostMapping
    public Result add(@RequestBody Pay pay){
        try {
            payService.add(pay);
            return Result.success();
        }catch (Exception e)
            {
                e.printStackTrace();
                log.error(e.getMessage());
                return Result.error(e.getMessage());
            }
    }

    @DeleteMapping({"/{id}"})
    public Result delete(@PathVariable("id") Integer payId){
        try {
            payService.delete(payId);
            return Result.success();
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @PutMapping
    public Result update(@RequestBody Pay pay){
        try {
            payService.update(pay);
            return Result.success();
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping
    public Result search(Integer pageNum, Integer pageSize, Pay pay, Authentication authentication){
        try {
            if (ObjectUtils.isEmpty(pageNum)) {
                User user=(User) authentication.getPrincipal();
                pay.setUserId(user.getUserId());
                List<Pay> orderList = payService.select(pay);
                return Result.success(orderList);
            } else {
                return Result.success(payService.searchForPage(pageNum, pageSize, pay));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable("id") Integer payId){
        try {
            return Result.success(payService.selectById(payId));
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
