package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 矿机列表(TwKuangji)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@Mapper
public interface TwKuangjiDao extends BaseMapper<TwKuangji> {

    List<TwKuangji> listpage(@Param("objectPage") Page<TwKuangji> objectPage, @Param("obj") PageParam pageParam);

}

