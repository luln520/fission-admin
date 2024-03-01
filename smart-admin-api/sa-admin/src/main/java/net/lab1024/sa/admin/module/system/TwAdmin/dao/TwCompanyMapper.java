package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_company(公司表)】的数据库操作Mapper
* @createDate 2024-02-15 17:42:54
* @Entity generator.domain.TwCompany
*/
@Mapper
public interface TwCompanyMapper extends BaseMapper<TwCompany> {

    List<TwCompany> listpage(@Param("objectPage") Page<TwCompany> objectPage, @Param("obj") CompanyVo companyVo);
}




