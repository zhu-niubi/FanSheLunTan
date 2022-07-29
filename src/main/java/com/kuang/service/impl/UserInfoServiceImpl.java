package com.kuang.service.impl;

import com.kuang.pojo.UserInfo;
import com.kuang.mapper.UserInfoMapper;
import com.kuang.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
