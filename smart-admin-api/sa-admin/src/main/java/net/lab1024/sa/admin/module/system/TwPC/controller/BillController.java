package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账单
 */
@RestController
@RequestMapping("/api/pc/bill")
public class BillController {


    /**
     * 账单列表
     * 表 bill
     * 参数： id （用户id）
     * select bill  where  uid=?  order by id desc limit 50
     * */

}

