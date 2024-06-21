package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserAgentMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserAgentService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
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
* @description 针对表【tw_user_agent(会员代理表)】的数据库操作Service实现
* @createDate 2024-06-12 17:34:31
*/
@Service
public class TwUserAgentServiceImpl extends ServiceImpl<TwUserAgentMapper, TwUserAgent>
    implements TwUserAgentService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwCompanyService twCompanyService;

    @Autowired
    private TwUserService twUserService;
    @Override
    public IPage<TwUserAgent> listpage(AgentVo agentVo, HttpServletRequest request) {
        List<TwUserAgent> list1 = new ArrayList<>();
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwUserAgent> objectPage = new Page<>(agentVo.getPageNum(), agentVo.getPageSize());
            List<TwUserAgent> listpage = baseMapper.listpage(objectPage, agentVo);
            for(TwUserAgent twUserAgent:listpage){
                Integer uid = twUserAgent.getUid();
                Integer oneUid = twUserAgent.getOneUid();
                Integer twoUid = twUserAgent.getTwoUid();
                Integer threeUid = twUserAgent.getThreeUid();


                TwUser byId1 = twUserService.getById(uid);
                String userCode = byId1.getUserCode();
                twUserAgent.setUserCode(userCode);

                if(oneUid != 0){
                    EmployeeEntity oneId = employeeService.getById(Long.valueOf(oneUid));
                    if(oneId != null){
                        twUserAgent.setOneName(oneId.getLoginName());
                    }else{
                        QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                        queryWrapper1.eq("id", oneId);
                        TwUser one = twUserService.getOne(queryWrapper1);
                        if(one != null){
                            twUserAgent.setOneName(one.getUsername());
                        }
                    }

                }
                if(twoUid != 0){
                    EmployeeEntity twoId = employeeService.getById(Long.valueOf(twoUid));
                    if(twoId != null){
                        twUserAgent.setTwoName(twoId.getLoginName());
                    }else{
                        QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                        queryWrapper1.eq("id", twoId);
                        TwUser one = twUserService.getOne(queryWrapper1);
                        if(one != null){
                            twUserAgent.setTwoName(one.getUsername());
                        }
                    }
                }
                if(threeUid != 0){
                    EmployeeEntity threeId = employeeService.getById(Long.valueOf(threeUid));
                    if(threeId != null){
                        twUserAgent.setThreeName(threeId.getLoginName());
                    }else{
                        QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                        queryWrapper1.eq("id", threeId);
                        TwUser one = twUserService.getOne(queryWrapper1);
                        if(one != null){
                            twUserAgent.setThreeName(one.getUsername());
                        }
                    }
                }
                list1.add(twUserAgent);

            }
            objectPage.setRecords(list1);
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwUserAgent> objectPage = new Page<>(agentVo.getPageNum(), agentVo.getPageSize());
                agentVo.setDepartmentId(byId.getDepartmentId());
                List<TwUserAgent> listpage = baseMapper.listpage(objectPage, agentVo);
                for(TwUserAgent twUserAgent:listpage){
                    Integer uid = twUserAgent.getUid();
                    Integer oneUid = twUserAgent.getOneUid();
                    Integer twoUid = twUserAgent.getTwoUid();
                    Integer threeUid = twUserAgent.getThreeUid();


                    TwUser byId1 = twUserService.getById(uid);
                    String userCode = byId1.getUserCode();
                    twUserAgent.setUserCode(userCode);

                    if(oneUid != 0){
                        EmployeeEntity oneId = employeeService.getById(Long.valueOf(oneUid));
                        if(oneId != null){
                            twUserAgent.setOneName(oneId.getLoginName());
                        }else{
                            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("id", oneId);
                            TwUser one = twUserService.getOne(queryWrapper1);
                            if(one != null){
                                twUserAgent.setOneName(one.getUsername());
                            }
                        }

                    }
                    if(twoUid != 0){
                        EmployeeEntity twoId = employeeService.getById(Long.valueOf(twoUid));
                        if(twoId != null){
                            twUserAgent.setTwoName(twoId.getLoginName());
                        }else{
                            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("id", twoId);
                            TwUser one = twUserService.getOne(queryWrapper1);
                            if(one != null){
                                twUserAgent.setTwoName(one.getUsername());
                            }
                        }
                    }
                    if(threeUid != 0){
                        EmployeeEntity threeId = employeeService.getById(Long.valueOf(threeUid));
                        if(threeId != null){
                            twUserAgent.setThreeName(threeId.getLoginName());
                        }else{
                            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("id", threeId);
                            TwUser one = twUserService.getOne(queryWrapper1);
                            if(one != null){
                                twUserAgent.setThreeName(one.getUsername());
                            }
                        }
                    }
                    list1.add(twUserAgent);

                }
                objectPage.setRecords(list1);
                return objectPage;
            }else{
                Page<TwUserAgent> objectPage = new Page<>(agentVo.getPageNum(), agentVo.getPageSize());
                if(inviteType == 1){
                    agentVo.setEmployeeId(byId.getEmployeeId());
                }
                List<TwUserAgent> listpage = baseMapper.listpage(objectPage, agentVo);
                for(TwUserAgent twUserAgent:listpage){
                    Integer uid = twUserAgent.getUid();
                    Integer oneUid = twUserAgent.getOneUid();
                    Integer twoUid = twUserAgent.getTwoUid();
                    Integer threeUid = twUserAgent.getThreeUid();


                    TwUser byId1 = twUserService.getById(uid);
                    String userCode = byId1.getUserCode();
                    twUserAgent.setUserCode(userCode);

                    if(oneUid != 0){
                        EmployeeEntity oneId = employeeService.getById(Long.valueOf(oneUid));
                        if(oneId != null){
                            twUserAgent.setOneName(oneId.getLoginName());
                        }else{
                            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("id", oneId);
                            TwUser one = twUserService.getOne(queryWrapper1);
                            if(one != null){
                                twUserAgent.setOneName(one.getUsername());
                            }
                        }

                    }
                    if(twoUid != 0){
                        EmployeeEntity twoId = employeeService.getById(Long.valueOf(twoUid));
                        if(twoId != null){
                            twUserAgent.setTwoName(twoId.getLoginName());
                        }else{
                            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("id", twoId);
                            TwUser one = twUserService.getOne(queryWrapper1);
                            if(one != null){
                                twUserAgent.setTwoName(one.getUsername());
                            }
                        }
                    }
                    if(threeUid != 0){
                        EmployeeEntity threeId = employeeService.getById(Long.valueOf(threeUid));
                        if(threeId != null){
                            twUserAgent.setThreeName(threeId.getLoginName());
                        }else{
                            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("id", threeId);
                            TwUser one = twUserService.getOne(queryWrapper1);
                            if(one != null){
                                twUserAgent.setThreeName(one.getUsername());
                            }
                        }
                    }
                    list1.add(twUserAgent);

                }
                objectPage.setRecords(list1);
                return objectPage;
            }
        }
        return null;
    }
}




