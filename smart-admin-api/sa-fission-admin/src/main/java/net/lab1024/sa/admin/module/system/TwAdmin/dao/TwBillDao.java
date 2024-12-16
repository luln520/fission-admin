package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 操作日志(TwBill)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
@Mapper
public interface TwBillDao extends BaseMapper<TwBill> {
    List<TwBill> listpage(@Param("objectPage") Page<TwBill> objectPage, @Param("obj") TwBillVo twBillVo);

    @Update("update tw_bill set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);
}

