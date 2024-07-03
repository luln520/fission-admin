package net.lab1024.sa.admin.module.system.TwPC.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwInviteSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwInviteSetService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pc/teamset")
@Api(tags = {AdminSwaggerTagConst.PC.PC_USER_INVITE})
public class PcInviteSetController {

    @Autowired
    private TwInviteSetService twInviteSetService;

    @GetMapping("/getTwLeverSet")
    @ResponseBody
    @ApiOperation(value = "获取团队规则设置")
    @NoNeedLogin
    public ResponseDTO<List<TwInviteSet>> getTwLeverSet(@RequestParam int companyId){
        return twInviteSetService.getTwLeverSet(companyId);
    }

}
