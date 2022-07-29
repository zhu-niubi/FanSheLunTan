package com.kuang.service.impl;

import com.kuang.pojo.Blog;
import com.kuang.mapper.BlogMapper;
import com.kuang.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
