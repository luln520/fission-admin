package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwFooter;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 合约订单表(TwHyorder)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
@Mapper
public interface TwHyorderDao extends BaseMapper<TwHyorder> {

    List<TwHyorder> listpage(@Param("objectPage") Page<TwHyorder> objectPage, @Param("obj") TwHyorderVo twHyorderVo);

    Integer countUnClosedOrders(@Param("companyId") Integer companyId);
    Integer countMockOrders(@Param("companyId") Integer companyId);
    Integer countHyOrdersDay(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("companyId") int companyId);
    Integer countHymockOrdersDay(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("companyId") int companyId);

}

