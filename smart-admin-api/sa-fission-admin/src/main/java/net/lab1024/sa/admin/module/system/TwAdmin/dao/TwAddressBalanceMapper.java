package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddressBalance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface TwAddressBalanceMapper extends BaseMapper<TwAddressBalance> {

    @Update("update tw_address_balance set balance = #{balance} where address_id = #{addressId}")
    void updateBalance(@Param("balance") BigDecimal balance, @Param("addressId") Integer addressId);
}
