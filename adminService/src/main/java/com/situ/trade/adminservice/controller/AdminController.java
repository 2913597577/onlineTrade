package com.situ.trade.adminservice.controller;

import com.situ.trade.commons.domian.entity.Admin;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.AdminService;
import com.situ.trade.commons.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController  //SpringMVC的注解
                    //@Controller 属于Spring的，是@Component的子注解，将当前类的对象放到spring的容器中
                    //@ResponseBody 可以修饰类，也可以修饰方法，以Json格式向客户端浏览器返回数据
@RequestMapping("/admin")  //SpringMVC的，为当前类的所有处理器方法，添加路径映射的前缀
@RequiredArgsConstructor //lombok的，添加一个包含所有final属性的构造器
@Slf4j      //lombok的，自动添加log的对象，用于日志的打印
public class AdminController {
    private final AdminService adminService;

    //管理员登录
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        try {
          admin= adminService.login(admin);
          //加密生成token
          String token= JWTUtil.createJWT(admin);
          admin.setPassword(null);
          return Result.success(token,admin);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
    //RESTful风格的API
    //添加 POST, 参数JSON
    @PostMapping// 相当于@RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin){
        try{
            adminService.add(admin);
            return Result.success();
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
            return Result.error(e.getMessage());
        }
    }
    //修改
    @PutMapping
    public Result edit(@RequestBody Admin admin){
        try {
            adminService.edit(admin);
            return Result.success();
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
            return Result.error(e.getMessage());
        }
    }
    //根据id传参
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        return Result.success(adminService.getById(id));
    }
    //多条件查询
    @GetMapping
    public Result search(Integer pageNum, Integer pageSize,Admin admin){
        if(ObjectUtils.isEmpty(pageNum)){
            return Result.success(adminService.search(admin));
        }else {
            return Result.success(adminService.searchForPage(pageNum,pageSize,admin));
        }
    }

}
