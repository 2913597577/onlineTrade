package com.situ.trade.userservice.controller;

import com.situ.trade.commons.domian.entity.Collect;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.CollectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/collect")
@Slf4j
public class CollectController {
    private final CollectService collectService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result add(@RequestBody Collect collect, Authentication authentication) {
        //获取当前登录的用户
        User user = (User) authentication.getPrincipal();

        collect.setUserId(user.getUserId());
        try {
            collectService.add(collect);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        try {
            collectService.remove(id,user.getUserId());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
    @GetMapping("/{goodsId}")
    public Result getByGoodsId(@PathVariable("goodsId") Integer goodsId, Authentication authentication) {

        //获取当前登录用户
        User user = (User) authentication.getPrincipal();
        //根据用户id和商品id查询收藏信息
        Collect collect= collectService.selectByUserIdAndGoodsId(user.getUserId(), goodsId);
        return Result.success(collect);
    }
    @GetMapping("/list")
    public Result getByUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (ObjectUtils.isEmpty(user)){
            return Result.error("用户未登录");
        }
        try {
            List<Collect> collect = collectService.selectByUserId(user.getUserId());
            return Result.success(collect);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping()
    public Result search(Integer pageNum, Integer pageSize,Collect collect) {
        if(ObjectUtils.isEmpty(pageNum)){
            return Result.success(collectService.search(collect));
        }
        else{
            return Result.success(collectService.searchForPage(pageNum,pageSize,collect));
        }
    }

}
