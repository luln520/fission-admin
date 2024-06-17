package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMockUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_mock_user_coin(模拟用户币种表)】的数据库操作Mapper
* @createDate 2024-06-02 15:18:53
* @Entity generator.domain.TwMockUserCoin
*/
@Mapper
public interface TwMockUserCoinMapper extends BaseMapper<TwMockUserCoin> {
    /**
     * 增加用户资产
     */
    Integer incre(@Param("uid") int uid, @Param("num") BigDecimal num, @Param("usdt") BigDecimal usdt);

    Integer decre(@Param("uid") int uid,@Param("num") BigDecimal num,@Param("usdt") BigDecimal usdt);

    BigDecimal afternum(@Param("uid") int uid);

    BigDecimal sumUserCoin(@Param("companyId") int companyId);

    List<TwMockUserCoin> listpage(@Param("objectPage") Page<TwMockUserCoin> objectPage, @Param("obj") TwUserVo twUserVo);
}




