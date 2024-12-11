package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBlock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface TwBlockMapper extends BaseMapper<TwBlock> {

    @Select("SELECT * FROM tw_block where chain_id = #{chainId} limit 1")
    TwBlock findByChainId( @Param("chainId") Integer chainId);

}
