package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwC2cMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.C2CVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
* @author 1
* @description 针对表【tw_c2c】的数据库操作Service实现
* @createDate 2024-02-25 20:52:34
*/
@Service
public class TwC2cServiceImpl extends ServiceImpl<TwC2cMapper, TwC2c>
    implements TwC2cService {

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private TokenService tokenService;


    @Autowired
    @Lazy
    private TwUserService twUserService;


    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Override
    public IPage<TwC2c> listpage(C2CVo c2CVo, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwC2c> objectPage = new Page<>(c2CVo.getPageNum(), c2CVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, c2CVo));
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwC2c> objectPage = new Page<>(c2CVo.getPageNum(), c2CVo.getPageSize());
                c2CVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, c2CVo));
                return objectPage;
            }else{
                Page<TwC2c> objectPage = new Page<>(c2CVo.getPageNum(), c2CVo.getPageSize());
                c2CVo.setEmployeeId(byId.getEmployeeId());
                objectPage.setRecords(baseMapper.listpage(objectPage, c2CVo));
                return objectPage;
            }
        }

        return null;
    }

    @Override
    public ResponseDTO rejectCoin(int id) {
        try{
            QueryWrapper<TwC2c> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            TwC2c one = this.getOne(queryWrapper);
            if(one != null){
                if(one == null ){
                    return  ResponseDTO.userErrorParam("提币订单不存在");
                }

                if(one.getStatus() != 1){
                    return  ResponseDTO.userErrorParam("此订单已处理");
                }

                one.setStatus(3);
                this.updateById(one);

                Integer uid = one.getUid();

                QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id", uid);
                TwUser twUser = twUserService.getOne(queryWrapper2);


                TwBill twBill = new TwBill();
                twBill.setUid(uid);
                twBill.setUserCode(twUser.getUserCode());
                twBill.setUsername(one.getUsername());
                twBill.setNum(one.getDzNum());
                twBill.setPath(twUser.getPath());
                twBill.setDepartment(twUser.getDepatmentId());
                twBill.setAfternum(twUserCoinService.afternum(uid));
                twBill.setType(19);
                twBill.setAddtime(new Date());
                twBill.setRemark("C2C提币审核驳回");
                twBillService.save(twBill);

                return ResponseDTO.okMsg("操作成功");

            }else{
                return ResponseDTO.userErrorParam("操作失败");
            }
        }catch (Exception e){
            return ResponseDTO.userErrorParam("操作失败");
        }

    }

    @Override
    public ResponseDTO confirmCoin(int id) {
        try{
            QueryWrapper<TwC2c> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            TwC2c one = this.getOne(queryWrapper);
            if(one == null ){
                return  ResponseDTO.userErrorParam("提币订单不存在");
            }

            if(one.getStatus() != 1){
                return  ResponseDTO.userErrorParam("此订单已处理");
            }
            Integer uid = one.getUid();

            one .setStatus(2);
            this.updateById(one);

            QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", one.getUid());
            TwUser twUser = twUserService.getOne(queryWrapper2);

            TwBill twBill = new TwBill();
            twBill.setUid(uid);
            twBill.setUserCode(twUser.getUserCode());
            twBill.setUsername(one.getUsername());
            twBill.setNum(one.getDzNum());
            twBill.setPath(twUser.getPath());
            twBill.setDepartment(twUser.getDepatmentId());
            twBill.setAfternum(twUserCoinService.afternum(uid));
            twBill.setType(18);
            twBill.setAddtime(new Date());
            twBill.setRemark("C2C提币审核成功");
            twBillService.save(twBill);


            return ResponseDTO.okMsg("操作成功");
        }catch (Exception e){
            return ResponseDTO.userErrorParam("操作失败");
        }
    }


    @Override
    public ResponseDTO reject(int id) {
        try {
            QueryWrapper<TwC2c> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            TwC2c one = this.getOne(queryWrapper);
            if (one == null) {
                return ResponseDTO.userErrorParam("C2C充值订单不存在");
            }

            if (one.getStatus() != 1) {
                return ResponseDTO.userErrorParam("此订单已处理");
            }
            one.setStatus(3);
            this.updateById(one);

            Integer uid = one.getUid();

            QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", one.getUid());
            TwUser twUser = twUserService.getOne(queryWrapper2);

            TwBill twBill = new TwBill();
            twBill.setUid(uid);
            twBill.setUserCode(twUser.getUserCode());
            twBill.setUsername(one.getUsername());
            twBill.setNum(one.getDzNum());
            twBill.setPath(twUser.getPath());
            twBill.setDepartment(twUser.getDepatmentId());
            twBill.setAfternum(twUserCoinService.afternum(uid));
            twBill.setType(19);
            twBill.setAddtime(new Date());
            twBill.setRemark("C2C充值审核驳回");
            twBillService.save(twBill);

            return ResponseDTO.okMsg("C2C充值审核驳回成功");
        } catch (Exception e) {
            return ResponseDTO.userErrorParam("C2C充值审核驳回失败");
        }
    }

    @Override
    public ResponseDTO confirm(int id) {
        QueryWrapper<TwC2c> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwC2c one = this.getOne(queryWrapper);
        if (one != null) {
            one.setStatus(2);
            this.updateById(one); //修改订单状态

            one.setStatus(2);
            this.updateById(one);

            Integer uid = one.getUid();

            QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", one.getUid());
            TwUser twUser = twUserService.getOne(queryWrapper2);

            TwBill twBill = new TwBill();
            twBill.setUid(uid);
            twBill.setUserCode(twUser.getUserCode());
            twBill.setUsername(one.getUsername());
            twBill.setNum(one.getDzNum());
            twBill.setPath(twUser.getPath());
            twBill.setDepartment(twUser.getDepatmentId());
            twBill.setAfternum(twUserCoinService.afternum(uid));
            twBill.setType(18);
            twBill.setAddtime(new Date());
            twBill.setRemark("C2C充值审核成功");
            twBillService.save(twBill);

            return ResponseDTO.okMsg("C2C充值成功");
        }
        return null;
    }
}




