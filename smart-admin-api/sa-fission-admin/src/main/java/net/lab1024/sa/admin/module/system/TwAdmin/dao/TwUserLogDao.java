package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户记录表(TwUserLog)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@Mapper
public interface TwUserLogDao extends BaseMapper<TwUserLog> {

    List<TwUserLog> listpage(@Param("objectPage") Page<TwUserLog> objectPage, @Param("obj") TwUserVo twUserVo);
}

