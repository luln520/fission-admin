package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
    Integer incre(@Param("uid") int uid, @Param("num") BigDecimal num, @Param("usdt") BigDecimal usdt);

    Integer decre(@Param("uid") int uid,@Param("num") BigDecimal num,@Param("usdt") BigDecimal usdt);

    BigDecimal afternum(@Param("uid") int uid);

    BigDecimal sumUserCoin(@Param("companyId") int companyId);

    List<TwUserCoin> listpage(@Param("objectPage") Page<TwUserCoin> objectPage, @Param("obj") TwUserVo twUserVo);


}

