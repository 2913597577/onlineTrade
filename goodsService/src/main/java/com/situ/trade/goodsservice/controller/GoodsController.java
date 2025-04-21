package com.situ.trade.goodsservice.controller;

import com.situ.trade.commons.domian.entity.Goods;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
@Slf4j
@CrossOrigin
public class GoodsController {
    private final GoodsService goodsService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result add(@RequestBody Goods goods) {
        try {
            goodsService.add(goods);
            return Result.success();
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
            return Result.error(e.getMessage());
        }
    }
    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result update(@RequestBody Goods goods) {
        try {
            goodsService.update(goods);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
    @DeleteMapping
    public Result delete(Integer goodsId) {
        try {
            goodsService.delete(goodsId);
            return Result.success();
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
            return Result.error(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Integer id) {
        return Result.success(goodsService.get(id));
    }
    @GetMapping
    public Result search(Integer pageNum, Integer pageSize,Goods goods) {
        if(ObjectUtils.isEmpty(pageNum)){
            return Result.success(goodsService.search(goods));
        }else {
            return Result.success(goodsService.searchForPage(pageNum, pageSize,goods));
        }
    }
}
