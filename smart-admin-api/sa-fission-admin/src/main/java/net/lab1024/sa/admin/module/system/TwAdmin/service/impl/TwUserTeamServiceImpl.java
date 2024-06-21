package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserTeamMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserTeam;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TeamVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserTeamService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_user_team(会员团队)】的数据库操作Service实现
* @createDate 2024-06-12 17:35:11
*/
@Service
public class TwUserTeamServiceImpl extends ServiceImpl<TwUserTeamMapper, TwUserTeam>
    implements TwUserTeamService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwCompanyService twCompanyService;

    @Autowired
    @Lazy
    private TwUserService twUserService;
    @Override
    public IPage<TwUserTeam> listpage(TeamVo teamVo, HttpServletRequest request) {
        List<TwUserTeam> list1 = new ArrayList<>();
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwUserTeam> objectPage = new Page<>(teamVo.getPageNum(), teamVo.getPageSize());
            List<TwUserTeam> listpage = baseMapper.listpage(objectPage, teamVo);
            for(TwUserTeam twUserTeam:listpage){
                Integer uid = twUserTeam.getUid();
                TwUser byId1 = twUserService.getById(uid);
                String userCode = byId1.getUserCode();
                twUserTeam.setUserCode(userCode);
                list1.add(twUserTeam);

            }
            objectPage.setRecords(list1);
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwUserTeam> objectPage = new Page<>(teamVo.getPageNum(), teamVo.getPageSize());
                teamVo.setDepartmentId(byId.getDepartmentId());
                List<TwUserTeam> listpage = baseMapper.listpage(objectPage, teamVo);
                for(TwUserTeam twUserTeam:listpage){
                    Integer uid = twUserTeam.getUid();
                    TwUser byId1 = twUserService.getById(uid);
                    String userCode = byId1.getUserCode();
                    twUserTeam.setUserCode(userCode);

                    list1.add(twUserTeam);

                }
                objectPage.setRecords(list1);
                return objectPage;
            }else{
                Page<TwUserTeam> objectPage = new Page<>(teamVo.getPageNum(), teamVo.getPageSize());
                if(inviteType == 1){
                    teamVo.setEmployeeId(byId.getEmployeeId());
                }
                List<TwUserTeam> listpage = baseMapper.listpage(objectPage, teamVo);
                for(TwUserTeam twUserTeam:listpage){
                    Integer uid = twUserTeam.getUid();
                    TwUser byId1 = twUserService.getById(uid);
                    String userCode = byId1.getUserCode();
                    twUserTeam.setUserCode(userCode);
                    list1.add(twUserTeam);
                }
                objectPage.setRecords(list1);
                return objectPage;
            }
        }
        return null;
    }
}




