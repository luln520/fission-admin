package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMessageRep;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinCommentService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.module.support.jwe.JweUserKey;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 后台管理员操作日志表(TwAdminLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:17:09
 */
@Service("twAdminLogService")
@Slf4j
public class TwAdminLogServiceImpl extends ServiceImpl<TwAdminLogDao, TwAdminLog> implements TwAdminLogService {

    @Autowired
    private TwUserDao twUserDao;
    @Autowired
    private TwMyzcDao twMyzcDao;
    @Autowired
    private TwRechargeDao twRechargeDao;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TwOnlineDao twOnlineDao;
    @Override
    public IPage<TwAdminLog> listpage(TwBillVo twBillVo,HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwAdminLog> objectPage = new Page<>(twBillVo.getPageNum(), twBillVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twBillVo));
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwAdminLog> objectPage = new Page<>(twBillVo.getPageNum(), twBillVo.getPageSize());
                twBillVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twBillVo));
                return objectPage;
            }else{
                Page<TwAdminLog> objectPage = new Page<>(twBillVo.getPageNum(), twBillVo.getPageSize());
                twBillVo.setEmployeeId(byId.getEmployeeId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twBillVo));
                return objectPage;
            }
        }

        return null;
    }

    @Override
    public boolean add(TwAdminLog twAdminLog) {
        return this.save(twAdminLog);
    }

    @Override
    public TwMessageRep message(HttpServletRequest request) {

        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);
        int companyId = byId.getCompanyId();
        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            TwMessageRep messageRep = new TwMessageRep();
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("rzstatus", 1);
            queryWrapper.eq("company_id", companyId);
            messageRep.setAuthCount(twUserDao.selectCount(queryWrapper).intValue());

            QueryWrapper<TwMyzc> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("status", 1);
            queryWrapper1.eq("company_id", companyId);
            messageRep.setMyzcCount(twMyzcDao.selectCount(queryWrapper1).intValue());

            QueryWrapper<TwRecharge> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("status", 1);
            queryWrapper2.eq("company_id", companyId);
            messageRep.setRechargeCount(twRechargeDao.selectCount(queryWrapper2).intValue());

            QueryWrapper<TwOnline> queryWrapper3= new QueryWrapper<>();
            queryWrapper3.eq("state", 0);
            queryWrapper3.eq("company_id", companyId);
            messageRep.setOnlineCount(twOnlineDao.selectCount(queryWrapper3).intValue());

            return messageRep;
        }


        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                TwMessageRep messageRep = new TwMessageRep();
                QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("rzstatus", 1);
                queryWrapper.eq("company_id", companyId);
                messageRep.setAuthCount(twUserDao.selectCount(queryWrapper).intValue());

                QueryWrapper<TwMyzc> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("status", 1);
                queryWrapper1.eq("company_id", companyId);
                messageRep.setMyzcCount(twMyzcDao.selectCount(queryWrapper1).intValue());

                QueryWrapper<TwRecharge> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("status", 1);
                queryWrapper2.eq("company_id", companyId);
                messageRep.setRechargeCount(twRechargeDao.selectCount(queryWrapper2).intValue());

                return messageRep;
            }
        }

        return null;
    }
}
