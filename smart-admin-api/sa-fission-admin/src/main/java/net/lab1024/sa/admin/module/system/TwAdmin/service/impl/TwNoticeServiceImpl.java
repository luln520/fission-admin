package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMyzcDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwNoticeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMyzcService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通知表(TwNotice)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
@Service("twNoticeService")
public class TwNoticeServiceImpl extends ServiceImpl<TwNoticeDao, TwNotice> implements TwNoticeService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TwCompanyService twCompanyService;
    @Override
    public IPage<TwNotice> listpage(PageParam pageParam, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);


        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwNotice> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwNotice> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
                pageParam.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
                return objectPage;
            }else{
                Page<TwNotice> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
                if(inviteType == 1){
                    pageParam.setEmployeeId(byId.getEmployeeId());
                }
                objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
                return objectPage;
            }
        }
        return null;
    }

    @Override
    public ResponseDTO<List<TwNotice>>  notice(int uid,int companyId) {
        QueryWrapper<TwNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        queryWrapper.eq("company_id",companyId);
        return ResponseDTO.ok(this.list(queryWrapper));
    }

    @Override
    public ResponseDTO<TwNotice> noticeDetail(int id) {
        QueryWrapper<TwNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return ResponseDTO.ok(this.getOne(queryWrapper));
    }

    @Override
    public ResponseDTO readone(int id) {
        QueryWrapper<TwNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwNotice one = this.getOne(queryWrapper);
        one.setStatus(2);
        this.updateById(one);
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO deleteOne(int id) {
        QueryWrapper<TwNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwNotice one = this.getOne(queryWrapper);
        this.removeById(one);
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO read(String token) {
        Long uidToken = tokenService.getUIDToken(token);
        QueryWrapper<TwNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uidToken.intValue());
        List<TwNotice> list = this.list(queryWrapper);
        for (TwNotice twNotice:list){
            twNotice.setStatus(2);
            this.updateById(twNotice);
        }
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO delete(String token) {
        Long uidToken = tokenService.getUIDToken(token);
        QueryWrapper<TwNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uidToken.intValue());
        List<TwNotice> list = this.list(queryWrapper);
        for (TwNotice twNotice:list){
            this.removeById(twNotice);
        }
        return ResponseDTO.ok();
    }
}
