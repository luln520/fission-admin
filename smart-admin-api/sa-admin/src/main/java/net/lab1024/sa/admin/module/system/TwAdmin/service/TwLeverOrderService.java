package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
* @author 1
* @description 针对表【tw_lever_order(杠杆订单表)】的数据库操作Service
* @createDate 2024-01-27 12:46:05
*/
public interface TwLeverOrderService extends IService<TwLeverOrder> {

    IPage<TwLeverOrder> listpage(LeverVo leverVo, HttpServletRequest request);

    ResponseDTO creatorder(int uid, String ccoinname, int win, int loss, int fold, int hyzd, BigDecimal num, BigDecimal ploss,BigDecimal premium,String language);

    ResponseDTO closeorder(int uid, int lid,String language);

    ResponseDTO editKonglo(int kongyk, int id);

}
