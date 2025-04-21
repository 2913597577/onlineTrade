package com.situ.trade.goodsservice.controller;

import com.situ.trade.commons.domian.entity.Type;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.TypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/type")
@Slf4j
@CrossOrigin  //允许跨域访问  前面不能有拦截器或者过滤器
public class TypeController {
    private final TypeService typeService;

    @PostMapping
    public Result add(@RequestBody Type type) {
        try {
            typeService.add(type);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @PutMapping
    public Result edit(@RequestBody Type type) {
        try {
            typeService.update(type);
            return Result.success();
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
            return Result.error(e.getMessage());
        }
    }

    @GetMapping
    public Result search(Integer pageNum, Integer pageSize, Type type) {
        if (ObjectUtils.isEmpty(pageNum)) {
            return Result.success(typeService.search(type));
        }else {
            return Result.success(typeService.searchForPage(pageNum,pageSize,type));
        }
    }
}
