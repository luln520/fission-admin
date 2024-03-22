package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.OnlineVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TwOnline)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
@Mapper
public interface TwOnlineDao extends BaseMapper<TwOnline> {

    List<TwOnline> listpage(@Param("objectPage") Page<TwOnline> objectPage, @Param("obj") OnlineVo onlineVo);

    List<TwOnline> getId(@Param("id") int id);


    int updateState(@Param("uid") int uid);

}

