package com.kuang.controller;


import com.kuang.mapper.DownloadMapper;
import com.kuang.pojo.Download;
import com.kuang.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  下载控制器
 * </p>
 *
 * @author 遇见狂神说
 * @since 2020-07-08
 */
@Controller
public class DownloadController {

    @Autowired
    DownloadMapper downloadMapper;

    @GetMapping({"/download"})
    public String download(Model model){
        List<Download> downloadList = downloadMapper.selectList(null);
        model.addAttribute("downloadList",downloadList);
        return "page/download";
    }

}

