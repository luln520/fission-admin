package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwNoticeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwOnlineDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwOnlineService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
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
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * (TwOnline)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
@Service("twOnlineService")
public class TwOnlineServiceImpl extends ServiceImpl<TwOnlineDao, TwOnline> implements TwOnlineService {

    @Autowired
    private TwUserService twUserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmployeeService employeeService;
    @Override
    public IPage<TwOnline> listpage(PageParam pageParam, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getKey().equals("admin") || roleEmployeeVO.getKey().equals("backend")){
            Page<TwOnline> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
            return objectPage;
        }

        if(roleEmployeeVO.getKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwOnline> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
                objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
                return objectPage;
            }else{
                Page<TwOnline> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
                objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
                return objectPage;
            }
        }
        return null;
    }

    @Override
    public List<TwOnline> lists(int uid,int companyId) {
        QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        queryWrapper.eq("company_id",companyId);
        queryWrapper.orderByDesc("addtime");
        return this.list(queryWrapper);
    }

    @Override
    public List<TwOnline> getId(int id) {
        return baseMapper.getId(id);
    }

    @Override
    public ResponseDTO backOnline(int id, String content) {
        try{
            QueryWrapper<TwOnline> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid",id);
            queryWrapper.orderByDesc("addtime").last("LIMIT 1"); // Replace "your_date_column" with the actual column you want to use for ordering.
            TwOnline one = getOne(queryWrapper);
            Integer uid = one.getUid();

            TwOnline one1 =new TwOnline();
            one1.setUid(one.getUid());
            one1.setUsername(one.getUsername());
            one1.setContent(content);
            one1.setType(1);
            one1.setAddtime(new Date());
            one1.setState(2);
            this.save(one1);

            this.baseMapper.updateState(uid);

            return ResponseDTO.ok("回复成功");

        }catch (Exception e){
            return ResponseDTO.userErrorParam("回复失败");
        }
    }

    @Override
    public ResponseDTO sendMsg(int uid, String content) {
        try{
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",uid);

            TwUser one = twUserService.getOne(queryWrapper);

            TwOnline one1 =new TwOnline();
            one1.setUid(one.getId());
            one1.setUsername(one.getUsername());
            one1.setContent(content);
            one1.setCompanyId(one.getCompanyId());
            one1.setType(2);
            one1.setAddtime(new Date());
            this.save(one1);

            return ResponseDTO.userErrorParam("发送成功");

        }catch (Exception e){
            return ResponseDTO.userErrorParam("发送失败");
        }

    }
}
