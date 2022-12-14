package com.kuang.service.impl;

import com.kuang.pojo.Comment;
import com.kuang.mapper.CommentMapper;
import com.kuang.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
