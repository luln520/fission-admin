package net.lab1024.sa.admin.module.system.TwPC.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 账单
 */
@RestController
@RequestMapping("/api/pc/bill")
public class BillController {

    @Autowired
    private TwBillService twBillService;

    /**
     * 账单列表
     * 表 bill
     * 参数： id （用户id）
     * select bill  where  uid=?  order by id desc limit 50
     * */
    @GetMapping("/list")
    @ApiOperation(value = "账单列表")
    @NoNeedLogin
    public ResponseDTO<List<TwBill>> list(@RequestParam int uid) {
        return ResponseDTO.ok(twBillService.lists(uid));
    }
}

