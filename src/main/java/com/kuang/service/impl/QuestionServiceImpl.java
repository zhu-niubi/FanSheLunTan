package com.kuang.service.impl;

import com.kuang.pojo.Question;
import com.kuang.mapper.QuestionMapper;
import com.kuang.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
