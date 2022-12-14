package com.kuang.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kuang.pojo.Blog;
import com.kuang.pojo.Comment;
import com.kuang.pojo.Question;
import com.kuang.pojo.UserInfo;
import com.kuang.result.R;
import com.kuang.service.*;
import com.kuang.utils.KuangUtils;
import com.kuang.vo.LayerPhoto;
import com.kuang.vo.LayerPhotoData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2020-06-28
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    BlogService blogService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/user/{uid}")
    public String userIndex(@PathVariable String uid, Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        // 用户的博客列表
        Page<Blog> pageParam = new Page<>(1, 10);
        blogService.page(pageParam,new QueryWrapper<Blog>().eq("author_id", uid)
                                                         .orderByDesc("gmt_create"));
        // 结果
        List<Blog> blogList = pageParam.getRecords();
        model.addAttribute("blogList",blogList);
        model.addAttribute("pageParam",pageParam);

        return "user/index";
    }

    @GetMapping("/user/blog/{uid}/{page}/{limit}")
    public String userIndexBlog(@PathVariable String uid,
                                @PathVariable int page,
                                @PathVariable int limit,
                                Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        // 用户的博客列表
        if (page < 1){
            page = 1;
        }
        Page<Blog> pageParam = new Page<>(page, limit);
        blogService.page(pageParam,new QueryWrapper<Blog>().eq("author_id", uid)
                .orderByDesc("gmt_create"));

        // 结果
        List<Blog> blogList = pageParam.getRecords();
        model.addAttribute("blogList",blogList);
        model.addAttribute("pageParam",pageParam);

        return "user/index";
    }

    @GetMapping("/user/question/{uid}/{page}/{limit}")
    public String userIndexQuestion(@PathVariable String uid,
                                @PathVariable int page,
                                @PathVariable int limit,
                                Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);

        //
        if (page < 1){
            page = 1;
        }
        Page<Question> pageParam = new Page<>(page, limit);
        questionService.page(pageParam,new QueryWrapper<Question>().eq("author_id", uid)
                .orderByDesc("gmt_create"));

        // 结果
        List<Question> blogList = pageParam.getRecords();
        model.addAttribute("questionList",blogList);
        model.addAttribute("pageParam",pageParam);

        return "user/user-question";
    }

    @GetMapping("/user/comment/{uid}/{page}/{limit}")
    public String userIndexComment(@PathVariable String uid,
                                    @PathVariable int page,
                                    @PathVariable int limit,
                                    Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);
        //
        if (page < 1){
            page = 1;
        }
        Page<Comment> pageParam = new Page<>(page, limit);
        commentService.page(pageParam,new QueryWrapper<Comment>().eq("user_id", uid)
                .orderByDesc("gmt_create"));

        // 结果
        List<Comment> commentList = pageParam.getRecords();
        model.addAttribute("commentList",commentList);
        model.addAttribute("pageParam",pageParam);

        return "user/user-comment";
    }

    // 用户信息回填
    private void userInfoCallBack(String uid,Model model){
        UserInfo userInfo = userInfoService.getById(uid);
        model.addAttribute("userInfo",userInfo);
        if (userInfo.getHobby()!=null && !userInfo.getHobby().equals("")){
            String[] hobbys = userInfo.getHobby().split(",");
            model.addAttribute("infoHobbys",hobbys);
        }
        // 获取用户的问题，博客，回复数
        int blogCount = blogService.count(new QueryWrapper<Blog>().eq("author_id", uid));
        int questionCount = questionService.count(new QueryWrapper<Question>().eq("author_id", uid));
        int commentCount = commentService.count(new QueryWrapper<Comment>().eq("user_id", uid));
        model.addAttribute("blogCount",blogCount);
        model.addAttribute("questionCount",questionCount);
        model.addAttribute("commentCount",commentCount);

    }

    // 捐赠layer弹窗二维码
    @GetMapping("/user/donate/{uid}")
    @ResponseBody
    public String userLayerDonate(@PathVariable String uid){
        // todo: 数据库设计
        ArrayList<LayerPhotoData> layerPhotos = new ArrayList<>();

        layerPhotos.add(new LayerPhotoData().setAlt("支付宝").setPid(1).setSrc("/images/donate/alipay.png").setThumb(""));
        layerPhotos.add(new LayerPhotoData().setAlt("微信").setPid(2).setSrc("/images/donate/wechat.jpg").setThumb(""));

        LayerPhoto donate = new LayerPhoto().setTitle("赞赏").setId(666).setStart(1);
        donate.setData(layerPhotos);

        String donateJsonString = JSONObject.toJSONString(donate);
        KuangUtils.print(donateJsonString);
        return donateJsonString;
    }

    // 更新头像
    //上传头像
    @ResponseBody
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public R uploadOssFile(@RequestParam("file") MultipartFile file){
        //获取上传的文件

        //返回上传到oss的路径
        String url =userService.uploadFileAvatar(file);

        //返回r对象
        return R.ok().data("url",url).message("文件上传成功");
    }

    @GetMapping("/user/update-avatar/{uid}")
    public String toUpdateAvatar(@PathVariable String uid,Model model){
        // 用户信息回填
        userInfoCallBack(uid,model);

        return "user/update-avatar";
    }

}

