package com.situ.trade.userservice.controller;

import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.domian.vo.UserVo;
import com.situ.trade.commons.service.UserService;
import com.situ.trade.commons.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserVo user) {
        //验证码的校验
        String sCaptcha=null;
        try {
            //解析验证码
            Claims claims = JWTUtil.parseJWT(user.getJwt());
            sCaptcha= claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error("验证码已经过期!");
        }
        try {
            if(!sCaptcha.equalsIgnoreCase(user.getCaptcha())){
                throw new Exception("验证码错误！");
            }
            User user1=userService.login(user);
            //生成Token
            String token= JWTUtil.createJWT(user1);
            user1.setPassword(null);
            user1.setPaypassword(null);
            return Result.success(token,user1);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    public Result reg(@RequestBody UserVo user) {
        //验证码的校验
        String sCaptcha=null;
        try {
            //解析验证码
            Claims claims = JWTUtil.parseJWT(user.getJwt());
            sCaptcha= claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error("验证码已经过期!");
        }
        try {
            if(!sCaptcha.equalsIgnoreCase(user.getCaptcha())){
                throw new Exception("验证码错误！");
            }
            userService.add(user);
            return Result.success();
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
            return Result.error(e.getMessage());
        }
    }
    @PutMapping
    public Result edit(@RequestBody User user) {
        try {
            userService.update(user);
            return Result.success();
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
            return Result.error(e.getMessage());
        }
    }
    @GetMapping(("/{id}"))
    public Result get(@PathVariable("id") Integer userId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if(!user.getUserId().equals(userId)){
            return Result.error("用户异常");
        }
        return Result.success(userService.getById(userId));
    }
    @GetMapping()
    public Result search(Integer pageNum, Integer pageSize,User user) {
        if(ObjectUtils.isEmpty(pageNum)){
            return Result.success(userService.search(user));
        }
        else{
            return Result.success(userService.searchForPage(pageNum,pageSize,user));
        }
    }
}
