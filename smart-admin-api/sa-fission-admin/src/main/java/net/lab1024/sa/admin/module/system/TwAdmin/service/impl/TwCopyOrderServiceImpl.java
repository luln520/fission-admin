package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCopyOrderMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyTake;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCopyOrderService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author 1
* @description 针对表【tw_copy_order(跟单表)】的数据库操作Service实现
* @createDate 2024-07-09 15:14:42
*/
@Service
public class TwCopyOrderServiceImpl extends ServiceImpl<TwCopyOrderMapper, TwCopyOrder>
    implements TwCopyOrderService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwCompanyService twCompanyService;
    @Override
    public IPage<TwCopyOrder> listpage(TwUserVo twUserVo, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwCopyOrder> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
            objectPage.setRecords(this.baseMapper.listpage(objectPage, twUserVo));
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwCopyOrder> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
                twUserVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(this.baseMapper.listpage(objectPage, twUserVo));
                return objectPage;
            }else{
                Page<TwCopyOrder> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
                if(inviteType == 1){
                    twUserVo.setEmployeeId(byId.getEmployeeId());
                }
                objectPage.setRecords(this.baseMapper.listpage(objectPage, twUserVo));
                return objectPage;
            }
        }
        return null;
    }
}




