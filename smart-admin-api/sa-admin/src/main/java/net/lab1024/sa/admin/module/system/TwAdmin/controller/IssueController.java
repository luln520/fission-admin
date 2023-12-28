package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import cn.hutool.core.date.DateUtil;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssue;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 认购
 */
@RestController
@RequestMapping("/api/admin/issue")
public class IssueController {

    /**
     * 认购项目记录
     */
    @GetMapping("/getIssueList")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO getIssueList() {
        // 查询 并返回  M('issue')->order('id desc')->select();

        return ResponseDTO.ok();
    }

    /**
     * 添加
     */
    @GetMapping("/addIssue")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO addIssue(TwIssue twIssue) {
        // 对象插入  issue 表

        return ResponseDTO.ok();
    }

    /**
     * 添加
     */
    @GetMapping("/updateIssue")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO updateIssue(TwIssue twIssue) {
        // 对象修改  issue 表  where id =?

        return ResponseDTO.ok();
    }

}

