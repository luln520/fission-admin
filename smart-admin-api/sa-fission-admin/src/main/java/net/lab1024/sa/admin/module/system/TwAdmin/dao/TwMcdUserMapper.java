package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdUser;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TwMcdUserMapper extends BaseMapper<TwMcdUser> {

    List<TwMcdUser> listpage(@Param("objectPage") Page<TwMcdUser> objectPage, @Param("obj") PageParam pageParam);

}
