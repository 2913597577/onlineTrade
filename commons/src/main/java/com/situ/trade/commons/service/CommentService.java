package com.situ.trade.commons.service;

import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Comment;

import java.util.List;

public interface CommentService {

    int add(Comment comment) throws Exception;

    int update(Comment comment);

    int delete(Integer id) throws Exception;

    Comment selectById(Integer id);

    List<Comment> selectByUserId(Integer userId);

    List<Comment> selectByUserIdAndGoodsId(Integer userId, Integer goodsId);

    PageInfo<Comment> searchForPage(Integer pageNum, Integer pageSize, Comment comment);

}
