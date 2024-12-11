package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TwTokenMapper extends BaseMapper<TwToken> {

    @Select("SELECT * FROM tw_token where chain_id = #{chainId} limit 1")
    TwToken findByChainId(@Param("chainId") Integer chainId);
}
