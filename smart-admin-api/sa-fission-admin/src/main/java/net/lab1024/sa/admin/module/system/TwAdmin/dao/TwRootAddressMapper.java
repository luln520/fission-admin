package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRootAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
@Mapper
public interface TwRootAddressMapper extends BaseMapper<TwRootAddress> {

    @Select("SELECT * FROM tw_root_address where chain_id = #{chainId} limit 1")
    TwRootAddress findByChainId(@Param("chainId") Integer chainId);

    @Update("update tw_root_address set step = #{step} where id = #{id}")
    void updateStep(@Param("id") Integer id, @Param("step") Integer step);
}
