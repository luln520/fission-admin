package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

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
    Integer incre(@Param("uid") int uid, @Param("num") BigDecimal num, @Param("coinname") String coinname);

    Integer decre(@Param("uid") int uid,@Param("num") BigDecimal num,@Param("coinname") String coinname);

    BigDecimal afternum(@Param("uid") int uid,@Param("coinname") String coinname);

    List<TwUserCoin> listpage(@Param("objectPage") Page<TwUserCoin> objectPage, @Param("obj") TwUserVo twUserVo);
}

