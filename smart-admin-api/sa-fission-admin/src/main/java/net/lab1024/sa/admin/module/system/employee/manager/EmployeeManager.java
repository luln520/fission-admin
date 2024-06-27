package net.lab1024.sa.admin.module.system.employee.manager;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserInvite;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserTeam;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserAgentService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserInviteService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserTeamService;
import net.lab1024.sa.admin.module.system.employee.dao.EmployeeDao;
import net.lab1024.sa.admin.module.system.role.domain.entity.RoleEmployeeEntity;
import net.lab1024.sa.admin.module.system.role.manager.RoleEmployeeManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工 manager
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-12-29 21:52:46
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Service
public class EmployeeManager extends ServiceImpl<EmployeeDao, EmployeeEntity> {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private RoleEmployeeManager roleEmployeeManager;

    @Autowired
    @Lazy
    private TwUserInviteService twUserInviteService;

    @Autowired
    @Lazy
    private TwUserAgentService twUserAgentService;

    @Autowired
    @Lazy
    private TwUserTeamService twUserTeamService;

    /**
     * 保存员工
     *
     * @param employee
     */
    @Transactional(rollbackFor = Throwable.class)
    public void saveEmployee(EmployeeEntity employee, List<Long> roleIdList) {
        // 保存员工 获得id
        employeeDao.insert(employee);

        if (CollectionUtils.isNotEmpty(roleIdList)) {
            List<RoleEmployeeEntity> roleEmployeeList = roleIdList.stream().map(e -> new RoleEmployeeEntity(e, employee.getEmployeeId())).collect(Collectors.toList());
            roleEmployeeManager.saveBatch(roleEmployeeList);
        }
        int employeeId =(int) employee.getEmployeeId();
        TwUserInvite twUserInvite = new TwUserInvite();
        twUserInvite.setUid(employeeId);
        twUserInvite.setCompanyId(employee.getCompanyId());
        twUserInvite.setCreateTime(new Date());
        twUserInvite.setUserCode(employee.getInvite());
        twUserInvite.setUsername(employee.getLoginName());
        twUserInviteService.save(twUserInvite);

        TwUserAgent twUserAgent = new TwUserAgent();
        twUserAgent.setThreeUid(0);
        twUserAgent.setTwoUid(0);
        twUserAgent.setOneUid(0);
        twUserAgent.setUsername(employee.getLoginName());
        twUserAgent.setDepartment(1);
        twUserAgent.setUid(employeeId);
        twUserAgent.setPath(String.valueOf(employeeId));
        twUserAgent.setCreateTime(new Date());
        twUserAgent.setCompanyId(employee.getCompanyId());
        twUserAgentService.save(twUserAgent);


        TwUserTeam twUserTeam = new TwUserTeam();
        twUserTeam.setNum(0);
        twUserTeam.setTotal(0);
        twUserTeam.setVoidNum(0);
        twUserTeam.setUsername(employee.getLoginName());
        twUserTeam.setUid(employeeId);
        twUserTeam.setAmount(new BigDecimal(0));
        twUserTeam.setCompanyId(employee.getCompanyId());
        twUserTeam.setPath(String.valueOf(employeeId));
        twUserTeam.setDepartment(1);
        twUserTeam.setCreateTime(new Date());
        twUserTeamService.save(twUserTeam);
    }

    /**
     * 更新员工
     *
     * @param employee
     */
    @Transactional(rollbackFor = Throwable.class)
    public void updateEmployee(EmployeeEntity employee, List<Long> roleIdList) {
        // 保存员工 获得id
        employeeDao.updateById(employee);

        if (CollectionUtils.isNotEmpty(roleIdList)) {
            List<RoleEmployeeEntity> roleEmployeeList = roleIdList.stream().map(e -> new RoleEmployeeEntity(e, employee.getEmployeeId())).collect(Collectors.toList());
            this.updateEmployeeRole(employee.getEmployeeId(), roleEmployeeList);
        }
    }

    /**
     * 更新员工角色
     *
     * @param employeeId
     * @param roleEmployeeList
     */
    @Transactional(rollbackFor = Throwable.class)
    public void updateEmployeeRole(Long employeeId, List<RoleEmployeeEntity> roleEmployeeList) {
        roleEmployeeManager.getBaseMapper().deleteByEmployeeId(employeeId);

        if (CollectionUtils.isNotEmpty(roleEmployeeList)) {
            roleEmployeeManager.saveBatch(roleEmployeeList);
        }
    }

}
