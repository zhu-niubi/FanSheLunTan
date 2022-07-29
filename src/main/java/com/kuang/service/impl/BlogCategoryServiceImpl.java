package com.kuang.service.impl;

import com.kuang.pojo.BlogCategory;
import com.kuang.mapper.BlogCategoryMapper;
import com.kuang.service.BlogCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {

}
