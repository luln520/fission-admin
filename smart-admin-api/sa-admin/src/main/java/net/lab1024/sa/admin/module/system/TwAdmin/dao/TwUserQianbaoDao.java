package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserQianbao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户钱包表(TwUserQianbao)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@Mapper
public interface TwUserQianbaoDao extends BaseMapper<TwUserQianbao> {

    List<TwUserQianbao> listpage(@Param("objectPage") Page<TwUserQianbao> objectPage, @Param("obj") TwUserVo twUserVo);
}

