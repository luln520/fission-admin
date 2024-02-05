package net.lab1024.sa.admin.module.system.TwAdmin.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 充值记录(TwRecharge)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
public interface TwRechargeService extends IService<TwRecharge> {

    BigDecimal sumDayRecharge(String startTime, String endTime);

    BigDecimal sumAllRecharge();

    IPage<TwRecharge> listpage(TwRechargeVo twRechargeVo, HttpServletRequest request);

    List<TwRecharge> listRecharge(int uid);

    ResponseDTO reject(int id);

    ResponseDTO confirm(int id);

    boolean rechargeNum(Integer id , @RequestParam BigDecimal num);

    ResponseDTO paycoin(int uid, String coinname,String czaddress,String  payimg, BigDecimal zznum,String czline);

}
