package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddress;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddressBalance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface TwAddressBalanceMapper extends BaseMapper<TwAddressBalance> {

    @Update("update tw_address_balance set balance = #{balance} where address_id = #{addressId} AND currency = #{currency}")
    void updateBalance(@Param("balance") BigDecimal balance, @Param("addressId") Integer addressId, @Param("currency") String currency);

    @Select("SELECT * FROM tw_address_balance where address_id = #{addressId} AND currency = #{currency} limit 1")
    TwAddressBalance findAddressBalance(@Param("addressId") Integer addressId, @Param("currency") String currency);

    @Select("select IFNULL(SUM(tab.balance), 0) from tw_address ta left join tw_address_balance tab on ta.id = tab.address_id where ta.company_id = #{companyId} AND tab.update_time BETWEEN FROM_UNIXTIME(#{startTime}) AND FROM_UNIXTIME(#{endTime})")
    BigDecimal amountInTime(@Param("startTime")Long startTime, @Param("endTime")Long endTime, @Param("companyId") int companyId);
}
