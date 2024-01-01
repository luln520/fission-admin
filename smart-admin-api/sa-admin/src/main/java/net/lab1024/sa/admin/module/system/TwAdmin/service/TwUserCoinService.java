package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

/**
 * 用户币种表(TwUserCoin)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
public interface TwUserCoinService extends IService<TwUserCoin> {

    /**
     * 增加用户资产
     * @param uid
     * @param num
     * @return
     */
     int incre(Integer uid, BigDecimal num, String coinname);

    /**
     * 减少用户资产
     * @param uid
     * @param num
     * @param coinname
     * @return
     */
     int decre(Integer uid,BigDecimal num,String coinname);

     double afternum(Integer uid,String coinname);


    IPage<TwUserCoin> listpage(TwUserVo twUserVo);

    TwUserCoin userCoin(int uid);

}
