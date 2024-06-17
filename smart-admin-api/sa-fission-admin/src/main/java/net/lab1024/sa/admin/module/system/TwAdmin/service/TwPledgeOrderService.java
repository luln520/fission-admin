package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2c;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwPledgeOrder;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_pledge_order(贷款订单表)】的数据库操作Service
* @createDate 2024-05-16 18:06:55
*/
public interface TwPledgeOrderService extends IService<TwPledgeOrder> {
    ResponseDTO reject(int id);

    ResponseDTO confirm(int id);

    ResponseDTO<List<TwPledgeOrder>> pledgeList(int uid);

    ResponseDTO<TwPledgeOrder> info(String orderNo);

    ResponseDTO pledge(TwPledgeOrder twPledgeOrder);
}
