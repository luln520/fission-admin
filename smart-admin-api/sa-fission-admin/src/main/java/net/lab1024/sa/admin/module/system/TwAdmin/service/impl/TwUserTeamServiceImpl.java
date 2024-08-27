package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMyzcDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwRechargeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserTeamMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private TwRechargeDao twRechargeDao;

    @Autowired
    private TwMyzcDao twMyzcDao;

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

                if(byId1 == null){
                    twUserTeam.setUserCode(uid.toString());
                }else{
                    String userCode = byId1.getUserCode();
                    twUserTeam.setUserCode(userCode);
                }
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
                    if(byId1 == null){
                        twUserTeam.setUserCode(uid.toString());
                    }else{
                        String userCode = byId1.getUserCode();
                        twUserTeam.setUserCode(userCode);
                    }

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
                    if(byId1 == null){
                        twUserTeam.setUserCode(uid.toString());
                    }else{
                        String userCode = byId1.getUserCode();
                        twUserTeam.setUserCode(userCode);
                    }
                    list1.add(twUserTeam);
                }
                objectPage.setRecords(list1);
                return objectPage;
            }
        }
        return null;
    }

    @Override
    public IPage<TwUser> teamlist(TeamVo teamVo, HttpServletRequest request) {
        List<TwUser> list1 = new ArrayList<>();
        Page<TwUser> objectPage = new Page<>(teamVo.getPageNum(), teamVo.getPageSize());
        for (TwUser twUser : baseMapper.teamlist(objectPage, teamVo)) {
            BigDecimal recharge = new BigDecimal(0);
            BigDecimal myzc = new BigDecimal(0);
            Integer uid = twUser.getId();

            QueryWrapper<TwRecharge> queryWrapper5 = new QueryWrapper<>();
            queryWrapper5.select("IFNULL(SUM(num), 0) as recharge")
                    .eq("uid", uid)
                    .eq("status", 2);

            List<Map<String, Object>> rechargeResult = this.twRechargeDao.selectMaps(queryWrapper5);
            if (rechargeResult.isEmpty()) {
                recharge = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            Object rechargeResultObject = rechargeResult.get(0).get("recharge");
            if (rechargeResultObject instanceof BigDecimal) {
                recharge =  ((BigDecimal) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (rechargeResultObject instanceof Long) {
                recharge =  BigDecimal.valueOf((Long) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (rechargeResultObject instanceof Integer) {
                recharge =  BigDecimal.valueOf((Integer) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                // 处理其他可能的类型
                recharge =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            QueryWrapper<TwMyzc> queryWrapper6 = new QueryWrapper<>();
            queryWrapper6.select("IFNULL(SUM(num), 0) as myzc")
                    .eq("userid", uid)
                    .eq("status", 2);

            List<Map<String, Object>> myzcResult = this.twMyzcDao.selectMaps(queryWrapper6);
            if (myzcResult.isEmpty()) {
                myzc = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            Object myzcResultObject = myzcResult.get(0).get("myzc");
            if (myzcResultObject instanceof BigDecimal) {
                myzc =  ((BigDecimal) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (myzcResultObject instanceof Long) {
                myzc =  BigDecimal.valueOf((Long) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else if (myzcResultObject instanceof Integer) {
                myzc =  BigDecimal.valueOf((Integer) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
            } else {
                // 处理其他可能的类型
                myzc =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            twUser.setRecharge(recharge);
            twUser.setMyzc(myzc);

            list1.add(twUser);
        }

        objectPage.setRecords(list1);
        return objectPage;
    }
}




