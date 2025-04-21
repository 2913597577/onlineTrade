package com.situ.trade.userservice.controller;

import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.util.CaptchaUtil;
import com.situ.trade.commons.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/captcha")
public class CaptchaController {

    @GetMapping
    public Result captcha(){
        //生成随机的验证码字符串
        String captcha= CaptchaUtil.randomCode(4);
        //生成Base64图片
        String base64=CaptchaUtil.getBase64(CaptchaUtil.createImage(100,32,captcha));
        //将字符串加密成Token
        String token= JWTUtil.createJWT(captcha);
        //返回Base64图片+Token
        return Result.success(200,token,base64);
    }
}
