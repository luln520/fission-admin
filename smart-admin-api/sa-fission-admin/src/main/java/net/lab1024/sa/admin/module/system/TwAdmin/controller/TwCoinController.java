package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 币种配置表(TwCoin)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
@RestController
@RequestMapping("/api/admin/coin")
@Api(tags = {AdminSwaggerTagConst.System.TW_COIN})
public class TwCoinController {
    /**
     * 服务对象
     */
    @Resource
    private TwCoinService twCoinService;


    /**
     * 所有获取币种   表Coin  order by sort asc
     */
    @PostMapping("/list")
    @ApiOperation(value = "币种配置列表")
    public ResponseDTO<IPage<TwCoin>> listpage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twCoinService.listpage(pageParam));
    }

    /**
     *添加 agent_id和name 都不能重复   表Coin
     */
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "公告新增或编辑")
    public ResponseDTO addOrUpdate(@RequestBody TwCoin twCoin, HttpServletRequest request) {
        return ResponseDTO.ok(twCoinService.addOrUpdate(twCoin,request));
    }

    /**
     * 设置 币状态 表Coin   修改status 1或者 2
     */
    @GetMapping("/updateStatus")
    @ApiOperation(value = "币种禁用启用")
    public ResponseDTO updateStatus(@RequestParam int id,@RequestParam Integer status) {
        return ResponseDTO.ok(twCoinService.updateStatus(id,status));
    }

    /**
     * 删除币 表Coin   where id=？
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除币")
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twCoinService.delete(id));
    }

    @GetMapping("/find")
    @ApiOperation(value = "删除币")
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twCoinService.find(id));
    }
}

