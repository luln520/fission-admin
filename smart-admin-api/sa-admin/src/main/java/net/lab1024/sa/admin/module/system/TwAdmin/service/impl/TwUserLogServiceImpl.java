package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户记录表(TwUserLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@Service("twUserLogService")
public class TwUserLogServiceImpl extends ServiceImpl<TwUserLogDao, TwUserLog> implements TwUserLogService {

    @Autowired
    @Lazy
    private TwUserService twUserService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmployeeService employeeService;
    @Override
    public IPage<TwUserLog> listpage(TwUserVo twUserVo, HttpServletRequest request) {

        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwUserLog> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
            List<TwUserLog> list = baseMapper.listpage(objectPage, twUserVo);
            for (TwUserLog twUserLog: list){
                Integer userid = twUserLog.getUserid();
                QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", userid);
                TwUser one = twUserService.getOne(queryWrapper);

                String username = one.getUsername();

                twUserLog.setUsername(username);
            }
            objectPage.setRecords(list);
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwUserLog> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
                twUserVo.setDepartmentId(byId.getDepartmentId());
                List<TwUserLog> list = baseMapper.listpage(objectPage, twUserVo);
                for (TwUserLog twUserLog: list){
                    Integer userid = twUserLog.getUserid();
                    QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id", userid);
                    TwUser one = twUserService.getOne(queryWrapper);

                    String username = one.getUsername();

                    twUserLog.setUsername(username);
                }
                objectPage.setRecords(list);
                return objectPage;
            }else{
                Page<TwUserLog> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
                twUserVo.setEmployeeId(byId.getEmployeeId());
                List<TwUserLog> list = baseMapper.listpage(objectPage, twUserVo);
                for (TwUserLog twUserLog: list){
                    Integer userid = twUserLog.getUserid();
                    QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id", userid);
                    TwUser one = twUserService.getOne(queryWrapper);

                    String username = one.getUsername();

                    twUserLog.setUsername(username);
                }
                objectPage.setRecords(list);
                return objectPage;
            }
        }

        return null;
    }
}
