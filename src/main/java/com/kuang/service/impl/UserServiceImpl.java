package com.kuang.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuang.pojo.User;
import com.kuang.mapper.UserMapper;
import com.kuang.pojo.UserRole;
import com.kuang.service.UserRoleService;
import com.kuang.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.utils.ConstantPropertiesUtil;
import com.kuang.utils.KuangUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Role;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

// UserDetailsService接口用于返回用户相关数据。
// 它有loadUserByUsername()方法，根据username查询用户实体，可以实现该接口覆盖该方法，实现自定义获取用户过程。
// 该接口实现类被DaoAuthenticationProvider 类使用，用于认证过程中载入用户信息。
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService,UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService roleService;
    @Autowired
    HttpSession session;

    // 用户登录逻辑和验证处理
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 通过用户名查询用户
        User user = userService.getOne(new QueryWrapper<User>().eq("username", s));

        // 放入session
        session.setAttribute("loginUser",user);

        //创建一个新的UserDetails对象，最后验证登陆的需要
        UserDetails userDetails=null;
        if(user!=null){
            //System.out.println("未加密："+user.getPassword());
            //String BCryptPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            // 登录后会将登录密码进行加密，然后比对数据库中的密码，数据库密码需要加密存储！
            String password = user.getPassword();

            //创建一个集合来存放权限
            Collection<GrantedAuthority> authorities = getAuthorities(user);
            //实例化UserDetails对象
            userDetails=new org.springframework.security.core.userdetails.User(s,password,
                            true,
                            true,
                            true,
                            true, authorities);
        }
        return userDetails;
    }

    // 获取角色信息
    private Collection<GrantedAuthority> getAuthorities(User user){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        UserRole role = roleService.getById(user.getRoleId());
        //注意：这里每个权限前面都要加ROLE_。否在最后验证不会通过
        authList.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        return authList;
    }


    @Override
    public String uploadFileAvatar(MultipartFile file) {
            //工具类获取值
            String endpoint = ConstantPropertiesUtil.END_POINT;
            String accessKeyId = ConstantPropertiesUtil.KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
            String bucketName = ConstantPropertiesUtil.BUCKET_NAME;


            InputStream inputStream = null;


            try {
                // 创建OSS实例。
                OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

                // 获取上传文件的输入流
                inputStream = file.getInputStream();

                //获取文件名称
                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                fileName = uuid + fileName;
                String datePath = new DateTime().toString("yyyy/MM/dd");
                //调用oss实例中的方法实现上传
                //参数1： Bucket名称
                //参数2： 上传到oss文件路径和文件名称 /aa/bb/1.jpg
                //参数3： 上传文件的输入流
                ossClient.putObject(bucketName, fileName, inputStream);
                // 关闭OSSClient。
                ossClient.shutdown();

                //把上传后文件路径返回
                //需要把上传到阿里云oss路径手动拼接出来
                //https://achang-edu.oss-cn-hangzhou.aliyuncs.com/default.gif
                String url = "http://"+bucketName+"."+endpoint+"/"+fileName ;

                return url;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }




}
