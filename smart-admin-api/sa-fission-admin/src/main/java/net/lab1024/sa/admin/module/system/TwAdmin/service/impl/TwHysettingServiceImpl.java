package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHysettingDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHyorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHysettingService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * 合约参数(TwHysetting)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:23:33
 */
@Service("twHysettingService")
public class TwHysettingServiceImpl extends ServiceImpl<TwHysettingDao, TwHysetting> implements TwHysettingService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwAdminLogService twAdminLogService;
    @Autowired
    private TwUserService twUserService;

    @Override
    public TwHysetting hysettingId(int companyId) {
        QueryWrapper<TwHysetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", companyId); // 添加查询条件
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean setWin(int id, int type, int uid, HttpServletRequest request) {

        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);

        QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id",uid);
        TwUser twUser = twUserService.getOne(queryWrapper1);

        QueryWrapper<TwHysetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", twUser.getCompanyId()); // 添加查询条件
        TwHysetting one = this.getOne(queryWrapper);
        if(one != null){

            if(type == 1){  //指定必赢
                String hyYlid = one.getHyYlid();
                String hyKsid = one.getHyKsid();
                String sethyYlid = addToEndOfId(hyYlid, String.valueOf(id));
                String sethyKsid = removeFromId(hyKsid, String.valueOf(id));
                one.setHyYlid(sethyYlid);
                one.setHyKsid(sethyKsid);
                this.updateById(one);

                TwAdminLog twAdminLog = new TwAdminLog();
                twAdminLog.setDepartment(twUser.getDepatmentId());
                twAdminLog.setPath(twUser.getPath());
                twAdminLog.setCompanyId(twUser.getCompanyId());
                twAdminLog.setAdminId((int) byId.getEmployeeId());
                twAdminLog.setAdminUsername(byId.getActualName());
                twAdminLog.setAction("指定必赢");
                long timestampInSeconds = Instant.now().getEpochSecond();
                twAdminLog.setCreateTime((int) timestampInSeconds);
                twAdminLog.setRemark("指定用户 "+twUser.getUsername()+" 必赢");
                twAdminLogService.save(twAdminLog);
            }

            if(type == 2){  //指定必输
                String hyYlid = one.getHyYlid();
                String hyKsid = one.getHyKsid();
                String sethyYlid = removeFromId(hyYlid,String.valueOf(id));
                String sethyKsid = addToEndOfId(hyKsid,String.valueOf(id));
                one.setHyYlid(sethyYlid);
                one.setHyKsid(sethyKsid);
                this.updateById(one);

                TwAdminLog twAdminLog = new TwAdminLog();
                twAdminLog.setDepartment(twUser.getDepatmentId());
                twAdminLog.setPath(twUser.getPath());
                twAdminLog.setCompanyId(twUser.getCompanyId());
                twAdminLog.setAdminId((int) byId.getEmployeeId());
                twAdminLog.setAdminUsername(byId.getActualName());
                twAdminLog.setAction("指定必输");
                long timestampInSeconds = Instant.now().getEpochSecond();
                twAdminLog.setCreateTime((int) timestampInSeconds);
                twAdminLog.setRemark("指定用户 "+twUser.getUsername()+" 必输");
                twAdminLogService.save(twAdminLog);
            }

            if(type == 3){  //正常输赢
                String hyYlid = one.getHyYlid();
                String hyKsid = one.getHyKsid();
                String sethyYlid = removeFromId(hyYlid,String.valueOf(id));
                String sethyKsid = removeFromId(hyKsid,String.valueOf(id));
                one.setHyYlid(sethyYlid);
                one.setHyKsid(sethyKsid);
                this.updateById(one);

                TwAdminLog twAdminLog = new TwAdminLog();
                twAdminLog.setDepartment(twUser.getDepatmentId());
                twAdminLog.setPath(twUser.getPath());
                twAdminLog.setCompanyId(twUser.getCompanyId());
                twAdminLog.setAdminId((int) byId.getEmployeeId());
                twAdminLog.setAdminUsername(byId.getActualName());
                twAdminLog.setAction("正常输赢");
                long timestampInSeconds = Instant.now().getEpochSecond();
                twAdminLog.setCreateTime((int) timestampInSeconds);
                twAdminLog.setRemark("指定用户 "+twUser.getUsername()+" 正常输赢");
                twAdminLogService.save(twAdminLog);
            }
        }
            return true;
    }


    @Override
    public boolean edit(TwHysetting twHysetting) {
        return this.saveOrUpdate(twHysetting);
    }

    private  String removeFromId(String hyset, String id) {
        // 使用 replace 方法删除目标字符串
        return hyset.replace(id + "|", "").replace("|" + id, "").replace(id, "");
    }

    private  String addToEndOfId(String hyset, String id) {
            // 使用拼接操作添加新的数字
        return hyset + "|" + id;
    }
}
