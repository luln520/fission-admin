package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMyzcVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

/**
 * 提币表(TwMyzc)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
public interface TwMyzcService extends IService<TwMyzc> {

    BigDecimal sumDayWithdraw(String startTime, String endTime);

    BigDecimal sumAllWithdraw();

    IPage<TwMyzc> listpage(TwMyzcVo twMyzcVo);
}
