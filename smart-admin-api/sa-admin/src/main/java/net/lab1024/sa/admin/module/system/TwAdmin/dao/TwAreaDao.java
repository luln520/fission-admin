package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TwArea)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:18:24
 */
@Mapper
public interface TwAreaDao extends BaseMapper<TwArea> {

    List<TwArea> lists();
}

