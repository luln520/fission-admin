package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddress;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TwAddressMapper extends BaseMapper<TwAddress> {

    List<TwAddress> listpage(@Param("objectPage") Page<TwAddress> objectPage, @Param("obj") AddressVo addressVo);

    int totalCount(@Param("obj") AddressVo addressVo);

    @Select("SELECT * FROM tw_address where chain_id = #{chainId} AND uid = #{uid} AND coin_id = #{coinId} limit 1")
    TwAddress findByChainId(@Param("uid") Integer uid, @Param("chainId") Integer chainId, @Param("coinId") Integer coinId);

    @Select("select ta.*, tab.currency, tab.balance from tw_address ta left join tw_address_balance tab on ta.id = tab.address_id where tab.balance > 0 AND ta.coin_id = #{coinId} limit #{limit}")
    List<TwAddress> listBalanceAddress(@Param("coinId") int coinId, @Param("limit") int limit);

    @Select("SELECT * FROM tw_address where address = #{address} limit 1")
    TwAddress findByAddress(@Param("address") String address);

    @Select("select * from tw_address")
    List<TwAddress> listAddress();
}
