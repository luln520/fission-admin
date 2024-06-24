package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwReport;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.ReportVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_report(报表)】的数据库操作Mapper
* @createDate 2024-06-12 17:34:50
* @Entity generator.domain.TwReport
*/
@Mapper
public interface TwReportMapper extends BaseMapper<TwReport> {

    List<TwReport> listpage(@Param("objectPage") Page<TwReport> objectPage, @Param("obj") ReportVo reportVo);

}




