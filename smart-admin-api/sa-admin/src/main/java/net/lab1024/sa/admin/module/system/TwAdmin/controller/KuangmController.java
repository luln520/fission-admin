package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.models.auth.In;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwDjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 矿机
 */
@RestController
@RequestMapping("/api/admin/kuangm")
public class KuangmController {

    /**
     * 矿机收益列表
     */
    @PostMapping("/djprofit")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO djprofit(String username) {
        if (StringUtils.hasLength(username)) {
            //查询单个用户的
            //矿机列表 查询并返回  M('djprofit')->where($where)->order('id desc')
        } else {

            //矿机列表 查询并返回  M('djprofit')->order('id desc')
        }
        return ResponseDTO.ok(null);
    }


    /**
     * 矿机收益列表
     */
    @PostMapping("/kjsylist")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO kjsylist(String username) {
        if (StringUtils.hasLength(username)) {
            //查询单个用户的
            //矿机列表 查询并返回  M('kjprofit')->where($where)->order('id desc')
        } else {

            //矿机列表 查询并返回  M('kjprofit')->order('id desc')
        }
        return ResponseDTO.ok(null);
    }


    /**
     * 启用会员矿机，停用会员矿机，删除会员矿机
     */
    @PostMapping("/userkjStatus")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO userkjStatus(Integer id, Integer type) {
        switch (type) {
            case 1:
                //修改status M("kjorder")->where($where)->save(array('status' => 1)); 启用
                break;
            case 2:
                //修改status  M("kjorder")->where($where)->save(array('status' => 2)); 停用
                break;
            case 3:
                //M("kjorder")->where($where)->delete(); 删除
                break;
        }
        //返回处理状态
        return ResponseDTO.ok(null);
    }

    /**
     * 会员过期的矿机列表
     */
    @PostMapping("/overlist")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO overlist() {
        // where status=3
        //数量 并返回 M('kjorder')->where($where)->count();

        //查询列表 并返回    M('kjorder')->where($where)->order('id desc')->select();
        return ResponseDTO.ok(null);
    }


    /**
     * 会员运行中的矿机列表
     */
    @PostMapping("/kjlist")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO kjlist(String username) {
        if (StringUtils.hasLength(username)) {
            //查询单个用户的
            //矿机列表 查询并返回  M('kjorder')->where(username)->order('id desc')
        } else {
            //查询列表 并返回     M('kjorder')->order('id desc')->select();
        }
        return ResponseDTO.ok(null);
    }


    /**
     * 启用，停用矿机，删除矿机
     */
    @PostMapping("/kuangjStatus")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO kuangjStatus(Integer id, Integer type) {
        switch (type) {
            case 1:
                //修改status M("kuangji")->where($where)->save(array('status' => 1)); 启用
                break;
            case 2:
                //修改status  M("kuangji")->where($where)->save(array('status' => 2)); 停用
                break;
            case 3:
                //M("kuangji")->where($where)->delete(); 删除
                break;
        }
        //返回处理状态
        return ResponseDTO.ok(null);
    }

    /**
     * 矿机列表
     */
    @PostMapping("/getkuangjiList")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO getkuangjiList() {
        //查询列表 并返回  M('kuangji')->order('id desc')->select();
        return ResponseDTO.ok(null);
    }


    /**
     * 添加矿机
     */
    @PostMapping("/addkj")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO addkj(TwKuangji twKuangji) {

        //接收矿机对象    写入 kuangji  表
        return ResponseDTO.ok(null);
    }

}

