package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户信息表(TwUser)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */
@Mapper
public interface TwUserDao extends BaseMapper<TwUser> {

    List<TwUser> listpage(@Param("objectPage") Page<TwUser> objectPage, @Param("obj") TwUserVo twUserVo);

    List<TwUser> authList(@Param("objectPage") Page<TwUser> objectPage, @Param("obj") TwUserVo twUserVo);
}

