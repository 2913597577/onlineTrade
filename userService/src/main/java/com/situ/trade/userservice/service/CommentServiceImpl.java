package com.situ.trade.userservice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.trade.commons.domian.entity.Comment;
import com.situ.trade.commons.service.CommentService;
import com.situ.trade.userservice.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    @Override
    public int add(Comment comment) throws Exception {
        return commentMapper.insert(comment);
    }

    @Override
    public int update(Comment comment) {
        return 0;
    }

    @Override
    public int delete(Integer id) throws Exception {
        return commentMapper.delete(id);
    }

    @Override
    public Comment selectById(Integer id) {
        return null;
    }

    @Override
    public List<Comment> selectByUserId(Integer userId) {
        return List.of();
    }

    @Override
    public List<Comment> selectByUserIdAndGoodsId(Integer userId, Integer goodsId) {
        return List.of();
    }

    @Override
    public PageInfo<Comment> searchForPage(Integer pageNum, Integer pageSize, Comment comment) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.select(comment);
        return new PageInfo<>(list);
    }
}
