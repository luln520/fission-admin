package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwReport;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.ReportVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 1
* @description 针对表【tw_report(报表)】的数据库操作Service
* @createDate 2024-06-12 17:34:50
*/
public interface TwReportService extends IService<TwReport> {

    IPage<TwReport> listpage(ReportVo reportVo, HttpServletRequest request);
}
