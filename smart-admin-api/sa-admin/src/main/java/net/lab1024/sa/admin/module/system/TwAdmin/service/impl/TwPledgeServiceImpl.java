package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwPledgeMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwPledgeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 1
* @description 针对表【tw_pledge(贷款表)】的数据库操作Service实现
* @createDate 2024-05-16 18:06:47
*/
@Service
public class TwPledgeServiceImpl extends ServiceImpl<TwPledgeMapper, TwPledge>
    implements TwPledgeService {

    @Override
    public TwPledge getTwPledge(int companyId) {
        QueryWrapper<TwPledge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", companyId); // 添加查询条件
        return this.getOne(queryWrapper);
    }

    @Override
    public ResponseDTO edit(TwPledge twPledge) {
        twPledge.setUpdateTime(new Date());
        return ResponseDTO.ok(this.updateById(twPledge));
    }



}




