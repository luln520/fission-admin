package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知
 */
@RestController
@RequestMapping("/api/pc/notice")
public class NoticeController {


    /**
     * 通知列表
     * 表 notice
     * 参数： id （用户 id）
     * select notice  where  uid=？
     * */

    /**
     * 通知详情
     * 参数： id （ 公告 id）
     * 表 notice
     * select notice  where  id=?
     * */

    /**
     * 标记全部已经读取
     * 参数：token
     * 表 notice
     * update notice set status=1  where  uid=?（通过 token 拿到id）
     * */

    /**
     * 标记单个已经读取
     * 参数：id(notice的id)
     * 表 notice
     * update notice set status=1  where  id=?
     * */

    /**
     * 删除我的全部通知
     * 参数： token
     * 表 notice
     * delete notice  where  uid=?（通过 token 拿到id）
     * */

    /**
     * 删除我单个通知
     * 参数： id(notice的id)
     * 表 notice
     * delete notice  where  id=?
     * */


}

