package com.kuang.service;

import com.kuang.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {
    //实现头像上传
    String uploadFileAvatar(MultipartFile file);
}
