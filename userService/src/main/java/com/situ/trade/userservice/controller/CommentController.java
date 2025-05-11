package com.situ.trade.userservice.controller;

import com.situ.trade.commons.domian.entity.Comment;
import com.situ.trade.commons.domian.vo.Result;
import com.situ.trade.commons.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Result add(@RequestBody Comment comment) {
        try {
            commentService.add(comment);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @PutMapping
    public Result edit(@RequestBody Comment comment) {
        try {
            commentService.update(comment);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        try {
            commentService.delete(id);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Integer id) {
        try {
            return Result.success(commentService.selectById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @GetMapping()
    public Result search(Integer pageNum, Integer pageSize, Comment comment) {
        try {
            return Result.success(commentService.searchForPage(pageNum, pageSize, comment));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

}
