package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwReceipt;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TwReceiptMapper extends BaseMapper<TwReceipt> {

    List<TwReceipt> listpage(@Param("objectPage") Page<TwReceipt> objectPage, @Param("obj") AddressVo addressVo);
}
