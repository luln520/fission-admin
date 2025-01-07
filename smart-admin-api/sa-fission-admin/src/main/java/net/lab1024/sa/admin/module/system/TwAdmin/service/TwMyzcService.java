package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.StatisticNumVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMyzcVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.WithDrawVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 提币表(TwMyzc)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
public interface TwMyzcService extends IService<TwMyzc> {

    BigDecimal sumDayWithdraw(String startTime, String endTime,int companyId);

    int usersCount(String startTime, String endTime,int companyId);
    int usersCountTotal(int companyId);

    BigDecimal sumAllWithdraw(int companyId);

    IPage<TwMyzc> listpage(TwMyzcVo twMyzcVo, HttpServletRequest request);

    List<TwMyzc> listPcpage(int uid);

    ResponseDTO rejectCoin(int id);

    ResponseDTO confirmCoin(int id);

    ResponseDTO tbhandle(int uid, int cid, String address, BigDecimal num, BigDecimal currenyNum,String currenyName,String language);

    StatisticNumVo statisticNum(String startDate, String endDate, int companyId);

    WithDrawVo staticWithDraw();
}
