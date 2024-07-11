package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyTake;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_copy_take(接单员表)】的数据库操作Mapper
* @createDate 2024-07-09 15:15:00
* @Entity generator.domain.TwCopyTake
*/

@Mapper
public interface TwCopyTakeMapper extends BaseMapper<TwCopyTake> {

    List<TwCopyTake> listpage(@Param("objectPage") Page<TwCopyTake> objectPage, @Param("obj") TwUserVo twUserVo);
}




