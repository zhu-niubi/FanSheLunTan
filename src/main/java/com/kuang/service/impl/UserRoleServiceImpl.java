package com.kuang.service.impl;

import com.kuang.pojo.UserRole;
import com.kuang.mapper.UserRoleMapper;
import com.kuang.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
