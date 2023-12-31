package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 钱包
 */
@RestController
@RequestMapping("/api/pc/finance")
public class FinanceController {

    /**
     * 提币列表
     * 表：myzc
     * 参数：id（用户id）
     * select myzc where userid=? order by id desc limit 15
     */


    /**
     * 充币列表
     * 表：recharge
     * 参数：id（用户id）
     * select recharge where uid=? order by id desc limit 15
     */
}

