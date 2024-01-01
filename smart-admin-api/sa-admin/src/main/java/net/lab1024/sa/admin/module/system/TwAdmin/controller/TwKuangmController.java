package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjprofitVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjprofitService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKuangjiService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 矿机
 */
@RestController
@RequestMapping("/api/admin/kuangm")
@Api(tags = {AdminSwaggerTagConst.System.TW_KUANGM})
public class TwKuangmController {

     @Autowired
     private TwKuangjiService twKuangjiService;

     @Autowired
     private TwKjorderService twKjorderService;

     @Autowired
     private TwKjprofitService twKjprofitService;

    /**
     * 矿机收益列表
     */
    @PostMapping("/kjsylist")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO kjsylist(@Valid @RequestBody TwKjprofitVo twKjprofitVo) {
        return ResponseDTO.ok(twKjprofitService.listpage(twKjprofitVo));
    }


    /**
     * 启用会员矿机收益，停用会员矿机收益，删除会员矿机收益
     */
    @PostMapping("/userkjStatus")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO userkjStatus(Integer id, Integer type) {
        switch (type) {
            case 1:
                //修改status M("kjorder")->where($where)->save(array('status' => 1)); 启用
                twKjorderService.open(id);
                break;
            case 2:
                //修改status  M("kjorder")->where($where)->save(array('status' => 2)); 停用
                twKjorderService.close(id);
                break;
            case 3:
                //M("kjorder")->where($where)->delete(); 删除
                twKjorderService.delete(id);
                break;
        }
        //返回处理状态
        return ResponseDTO.ok();
    }


    /**
     * 会员运行中的矿机列表
     */
    @PostMapping("/kjlist")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO<IPage<TwKjorder>>  kjlist(@Valid @RequestBody TwKjorderVo twKjorderVo) {
        return ResponseDTO.ok(twKjorderService.listpage(twKjorderVo));
    }


    /**
     * 启用，停用矿机，删除矿机
     */
    @GetMapping("/kuangjStatus")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO kuangjStatus(@RequestParam Integer id,@RequestParam Integer type) {
        switch (type) {
            case 1:
                //修改status M("kuangji")->where($where)->save(array('status' => 1)); 启用
                twKuangjiService.open(id);
                break;
            case 2:
                //修改status  M("kuangji")->where($where)->save(array('status' => 2)); 停用
                twKuangjiService.close(id);
                break;
            case 3:
                //M("kuangji")->where($where)->delete(); 删除
                twKuangjiService.delete(id);
                break;
        }
        //返回处理状态
        return ResponseDTO.ok();
    }

    /**
     * 矿机列表
     */
    @PostMapping("/getkuangjiList")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO<IPage<TwKuangji>>  getkuangjiList(@Valid @RequestBody PageParam pageParam) {
        //查询列表 并返回  M('kuangji')->order('id desc')->select();
         return ResponseDTO.ok(twKuangjiService.listpage(pageParam));
    }

    /**
     * 添加矿机
     */
    @PostMapping("/addkj")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO addkj(@RequestBody TwKuangji twKuangji) {
       return ResponseDTO.ok(twKuangjiService.addkj(twKuangji));
    }

}

