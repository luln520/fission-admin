package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * h5、pc公用文件上传接口
 */
@RestController
@RequestMapping("/api/pc/file")
public class FileController {
    /**
     * 文件上传接口   file（文件流） baseFileName（文件夹名称） 需要开放静态文件访问路径
     * 1.限制file类型为 图片 和 10M以内('jpg', 'gif', 'png', 'jpeg')
     * 2.只需要把文件 写入对应 baseFileName （文件夹名称）就行 基础存放路径自定义（开放为静态访问路径就行）
     * 3.返回文件相对 路径   baseFileName/xxx.png(jpg)
     */


}

