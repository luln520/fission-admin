package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwReportMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwReport;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.ReportVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwReportService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_report(报表)】的数据库操作Service实现
* @createDate 2024-06-12 17:34:50
*/
@Service
public class TwReportServiceImpl extends ServiceImpl<TwReportMapper, TwReport>
    implements TwReportService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwCompanyService twCompanyService;
    @Override
    public IPage<TwReport> listpage(ReportVo reportVo, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwReport> objectPage = new Page<>(reportVo.getPageNum(), reportVo.getPageSize());
            List<TwReport> listpage = baseMapper.listpage(objectPage, reportVo);
            objectPage.setRecords(listpage);
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwReport> objectPage = new Page<>(reportVo.getPageNum(), reportVo.getPageSize());
                reportVo.setDepartmentId(byId.getDepartmentId());
                List<TwReport> listpage = baseMapper.listpage(objectPage, reportVo);
                objectPage.setRecords(listpage);
                return objectPage;
            }else{
                Page<TwReport> objectPage = new Page<>(reportVo.getPageNum(), reportVo.getPageSize());
                if(inviteType == 1){
                    reportVo.setEmployeeId(byId.getEmployeeId());
                }
                List<TwReport> listpage = baseMapper.listpage(objectPage, reportVo);
                objectPage.setRecords(listpage);
                return objectPage;
            }
        }
        return null;
    }
}




