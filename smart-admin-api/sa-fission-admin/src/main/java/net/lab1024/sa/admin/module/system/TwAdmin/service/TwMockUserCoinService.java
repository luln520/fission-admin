package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMockUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;

import java.math.BigDecimal;

/**
* @author 1
* @description 针对表【tw_mock_user_coin(模拟用户币种表)】的数据库操作Service
* @createDate 2024-06-02 15:18:54
*/
public interface TwMockUserCoinService extends IService<TwMockUserCoin> {
    /**
     * 增加用户资产
     * @param uid
     * @param num
     * @return
     */
    int incre(Integer uid, BigDecimal num, BigDecimal usdt);

    /**
     * 减少用户资产
     * @param uid
     * @param num
     * @return
     */
    int decre(Integer uid,BigDecimal num,BigDecimal usdt);

    BigDecimal afternum(Integer uid);

    BigDecimal sumUserCoin(int companyId);


    IPage<TwMockUserCoin> listpage(TwUserVo twUserVo);

    TwMockUserCoin userCoin(int uid);
}
