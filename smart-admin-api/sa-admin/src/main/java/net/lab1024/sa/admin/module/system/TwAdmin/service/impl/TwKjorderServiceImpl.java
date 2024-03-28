package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 矿机订单表(TwKjorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
@Service("twKjorderService")
public class TwKjorderServiceImpl extends ServiceImpl<TwKjorderDao, TwKjorder> implements TwKjorderService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TwCompanyService twCompanyService;
    @Autowired
    private TwUserCoinService twUserCoinService;
    @Override
    public IPage<TwKjorder> listpage(TwKjorderVo twKjorderVo, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwKjorder> objectPage = new Page<>(twKjorderVo.getPageNum(), twKjorderVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twKjorderVo));
            return objectPage;
        }
        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwKjorder> objectPage = new Page<>(twKjorderVo.getPageNum(), twKjorderVo.getPageSize());
                twKjorderVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twKjorderVo));
                return objectPage;
            }else{
                Page<TwKjorder> objectPage = new Page<>(twKjorderVo.getPageNum(), twKjorderVo.getPageSize());
                if(inviteType == 1){
                    twKjorderVo.setEmployeeId(byId.getEmployeeId());
                }
                objectPage.setRecords(baseMapper.listpage(objectPage, twKjorderVo));
                return objectPage;
            }
        }

        return null;
    }

    @Override
    public List<TwKjorder> uidList(int uid) {
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid); // 添加查询条件
        return this.list(queryWrapper);
    }

    @Override
    public int countAllOrders(int companyId) {
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1"); // 添加查询条件
        queryWrapper.eq("company_id", companyId); // 添加查询条件
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public boolean open(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKjorder one = this.getOne(queryWrapper);
        one.setStatus(1);
        return this.updateById(one);
    }
    @Override
    public boolean close(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKjorder one = this.getOne(queryWrapper);
        one.setStatus(2);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public ResponseDTO redeem(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKjorder one = this.getOne(queryWrapper);
        one.setStatus(3);
        this.updateById(one);
        Integer uid = one.getUid();

        QueryWrapper<TwUserCoin> queryCoin = new QueryWrapper<>();
        queryCoin.eq("userid", uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryCoin);
        BigDecimal buynum = one.getBuynum();
        twUserCoinService.incre(uid,buynum,twUserCoin.getUsdt());

        return ResponseDTO.ok();
    }
}
