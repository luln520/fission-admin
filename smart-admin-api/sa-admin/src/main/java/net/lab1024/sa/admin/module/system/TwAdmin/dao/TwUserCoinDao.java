package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户币种表(TwUserCoin)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@Mapper
public interface TwUserCoinDao extends BaseMapper<TwUserCoin> {

    /**
     * 增加用户资产
     */
    Integer incre(@Param("uid") int uid,@Param("num") double num,@Param("coinname") String coinname);

    double afternum(@Param("uid") int uid,@Param("coinname") String coinname);
}

