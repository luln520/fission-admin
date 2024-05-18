package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwPledge;
import net.lab1024.sa.common.common.domain.ResponseDTO;

/**
* @author 1
* @description 针对表【tw_pledge(贷款表)】的数据库操作Service
* @createDate 2024-05-16 18:06:47
*/
public interface TwPledgeService extends IService<TwPledge> {

    TwPledge getTwPledge(int companyId);
    ResponseDTO edit(TwPledge twPledge);

}
