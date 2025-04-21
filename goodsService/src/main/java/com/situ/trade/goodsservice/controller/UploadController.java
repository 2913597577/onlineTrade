package com.situ.trade.goodsservice.controller;

import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.util.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
    @Value("${upload.path}")
    private String path;

    @PostMapping
    public Result upload(MultipartFile file) {
        String fileName=UploadUtil.save(file, path);
        return Result.success(fileName);
    }
}
