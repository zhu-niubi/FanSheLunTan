package com.kuang.service.impl;

import com.kuang.pojo.Say;
import com.kuang.mapper.SayMapper;
import com.kuang.service.SayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SayServiceImpl extends ServiceImpl<SayMapper, Say> implements SayService {

}
