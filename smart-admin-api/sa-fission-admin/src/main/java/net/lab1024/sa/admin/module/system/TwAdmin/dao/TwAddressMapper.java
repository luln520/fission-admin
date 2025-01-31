package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddress;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TwAddressMapper extends BaseMapper<TwAddress> {

    List<TwAddress> listpage(@Param("objectPage") Page<TwAddress> objectPage, @Param("obj") AddressVo addressVo);

    int totalCount(@Param("obj") AddressVo addressVo);

    @Select("SELECT * FROM tw_address where chain_id = #{chainId} AND uid = #{uid} AND coin_id = #{coinId} limit 1")
    TwAddress findByChainId(@Param("uid") Integer uid, @Param("chainId") Integer chainId, @Param("coinId") Integer coinId);

    @Select("select ta.*, tab.currency, tab.balance from tw_address ta left join tw_address_balance tab on ta.id = tab.address_id where ta.coin_id = #{coinId} order by ta.update_time desc")
    List<TwAddress> listIdleAddress(@Param("coinId") Integer coinId);

    @Select("select ta.*, tab.currency, tab.balance from tw_address ta left join tw_address_balance tab on ta.id = tab.address_id where tab.balance > 0 AND ta.coin_id = #{coinId} order by tab.balance desc")
    List<TwAddress> listBalanceAddress(@Param("coinId") int coinId);

    @Select("SELECT * FROM tw_address where address = #{address} limit 1")
    TwAddress findByAddress(@Param("address") String address);

    @Select("SELECT * FROM tw_address where id = #{id}")
    TwAddress findById(@Param("id") int id);

    @Select("select * from tw_address")
    List<TwAddress> listAddress();

    @Select("SELECT * FROM tw_address where coin_id = #{coinId}")
    List<TwAddress> listCoinAddress(@Param("coinId") int coinId);

    @Update("update tw_address set block_number = #{blockNumber}, update_time = update_time where id = #{id}")
    int updateAddressBlock(@Param("id") int id, @Param("blockNumber") int blockNumber);

    @Select("<script>SELECT * FROM tw_address WHERE address IN <foreach item='address' collection='list' open='(' separator=',' close=')'> #{address}</foreach></script>")
    List<TwAddress> selectByAddresses(@Param("list") List<String> addressList);

    @Update("update tw_address set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);

    @Update("update tw_address set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%') AND uid = #{uid}")
    int updatePathPer(@Param("sourceId") int sourceId, @Param("destId") int destId, @Param("uid") int uid);
}
