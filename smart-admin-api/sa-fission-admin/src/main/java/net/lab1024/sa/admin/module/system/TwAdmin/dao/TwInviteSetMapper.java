package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwInviteSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_invite_set(邀请规则设置)】的数据库操作Mapper
* @createDate 2024-07-03 14:47:22
* @Entity generator.domain.TwInviteSet
*/
@Mapper
public interface TwInviteSetMapper extends BaseMapper<TwInviteSet> {

    List<TwInviteSet> listpage(@Param("objectPage") Page<TwInviteSet> objectPage, @Param("obj") PageParam pageParam);
}




