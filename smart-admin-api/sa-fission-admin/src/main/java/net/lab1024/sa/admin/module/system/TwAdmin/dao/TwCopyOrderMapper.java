package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyTake;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_copy_order(跟单表)】的数据库操作Mapper
* @createDate 2024-07-09 15:14:42
* @Entity generator.domain.TwCopyOrder
*/
@Mapper
public interface TwCopyOrderMapper extends BaseMapper<TwCopyOrder> {

    List<TwCopyOrder> listpage(@Param("objectPage") Page<TwCopyOrder> objectPage, @Param("obj") TwUserVo twUserVo);
}




