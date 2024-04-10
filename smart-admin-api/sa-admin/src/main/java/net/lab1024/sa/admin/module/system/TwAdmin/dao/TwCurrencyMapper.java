package net.lab1024.sa.admin.module.system.TwAdmin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCurrency;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_currency(货币表)】的数据库操作Mapper
* @createDate 2024-04-10 16:25:03
* @Entity generator.domain.TwCurrency
*/
@Mapper
public interface TwCurrencyMapper extends BaseMapper<TwCurrency> {
    List<TwCurrency> listpage(@Param("objectPage") Page<TwCurrency> objectPage, @Param("obj") CompanyVo companyVo);

}
