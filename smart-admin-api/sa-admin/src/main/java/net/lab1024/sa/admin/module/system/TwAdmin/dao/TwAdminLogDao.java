package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 后台管理员操作日志表(TwAdminLog)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:17:08
 */
@Mapper
public interface TwAdminLogDao extends BaseMapper<TwAdminLog> {

    List<TwAdminLog> listpage(@Param("objectPage") Page<TwAdminLog> objectPage, @Param("obj") TwBillVo twBillVo);
}

