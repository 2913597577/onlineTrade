package com.situ.trade.userservice.controller;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Stores;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.StoresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Slf4j
public class StoresController {

    private final StoresService storesService;

    @PostMapping
    public Result add(@RequestBody Stores stores, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        stores.setUserId(user.getUserId());
        try {
            return storesService.add(stores);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @PutMapping
    public Result edit(@RequestBody Stores stores) {
        try {
            storesService.edit(stores);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping
    public Result search(Integer pageNum, Integer pageSize,Stores stores) {
        try {
            PageInfo<Stores> pageInfo = storesService.searchForPage(pageNum, pageSize, stores);
            return Result.success(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        try {
            Stores stores = storesService.getById(id);
            return Result.success(stores);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
    @GetMapping("/user/{userId}")
    public Result getByUserId(@PathVariable("userId") Integer userId,Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            if(!user.getUserId().equals(userId)){
                return Result.error("用户异常");
            }
            Stores stores = storesService.searchByUserId(userId);
            return Result.success(stores);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

}
