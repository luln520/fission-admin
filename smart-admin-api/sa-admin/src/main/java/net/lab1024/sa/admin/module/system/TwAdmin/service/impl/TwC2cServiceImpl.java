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
import net.lab1024.sa.common.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberService;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private TwC2cBankService twC2cBankService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private TwAreaService twAreaService;

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
    public ResponseDTO info(int id) {
        QueryWrapper<TwC2c> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return ResponseDTO.ok(this.getOne(queryWrapper));
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


                one.setStatus(3);
                this.updateById(one);


                QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("user_code", one.getUserCode());
                TwUser twUser = twUserService.getOne(queryWrapper2);


                TwBill twBill = new TwBill();
                twBill.setUid(twUser.getId());
                twBill.setUserCode(twUser.getUserCode());
                twBill.setUsername(one.getUsername());
                twBill.setNum(one.getDzNum());
                twBill.setCoinname("USDT");
                twBill.setPath(twUser.getPath());
                twBill.setDepartment(twUser.getDepatmentId());
                twBill.setAfternum(twUserCoinService.afternum(twUser.getId()));
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

            one .setStatus(2);
            this.updateById(one);

            QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("user_code", one.getUserCode());
            TwUser twUser = twUserService.getOne(queryWrapper2);

            TwBill twBill = new TwBill();
            twBill.setUid(twUser.getId());
            twBill.setUserCode(twUser.getUserCode());
            twBill.setUsername(one.getUsername());
            twBill.setNum(one.getDzNum());
            twBill.setCoinname("USDT");
            twBill.setPath(twUser.getPath());
            twBill.setDepartment(twUser.getDepatmentId());
            twBill.setAfternum(twUserCoinService.afternum(twUser.getId()));
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
            QueryWrapper<TwC2c> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            TwC2c one = this.getOne(queryWrapper);
            if (one == null) {
                return ResponseDTO.userErrorParam("C2C充值订单不存在");
            }
            one.setStatus(3);
            this.updateById(one);


            QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("user_code", one.getUserCode());
            TwUser twUser = twUserService.getOne(queryWrapper2);

            TwBill twBill = new TwBill();
            twBill.setUid(twUser.getId());
            twBill.setUserCode(twUser.getUserCode());
            twBill.setUsername(one.getUsername());
            twBill.setNum(one.getDzNum());
            twBill.setCoinname("USDT");
            twBill.setPath(twUser.getPath());
            twBill.setDepartment(twUser.getDepatmentId());
            twBill.setAfternum(twUserCoinService.afternum(twUser.getId()));
            twBill.setType(19);
            twBill.setAddtime(new Date());
            twBill.setRemark("C2C充值审核驳回");
            twBillService.save(twBill);

            return ResponseDTO.okMsg("C2C充值审核驳回成功");

    }

    @Override
    public ResponseDTO confirm(int id) {
        QueryWrapper<TwC2c> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwC2c one = this.getOne(queryWrapper);
        if (one != null) {
            one.setStatus(2);
            this.updateById(one); //修改订单状态


            QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("user_code", one.getUserCode());
            TwUser twUser = twUserService.getOne(queryWrapper2);

            TwBill twBill = new TwBill();
            twBill.setUid(twUser.getId());
            twBill.setUserCode(twUser.getUserCode());
            twBill.setUsername(one.getUsername());
            twBill.setCoinname("USDT");
            twBill.setNum(one.getDzNum());
            twBill.setPath(twUser.getPath());
            twBill.setDepartment(twUser.getDepatmentId());
            twBill.setAfternum(twUserCoinService.afternum(twUser.getId()));
            twBill.setType(18);
            twBill.setAddtime(new Date());
            twBill.setRemark("C2C充值审核成功");
            twBillService.save(twBill);

            return ResponseDTO.okMsg("C2C充值成功");
        }
        return null;
    }

    @Override
    public ResponseDTO bankInfo(TwC2cBank c2cBank) {
        String orderNo = c2cBank.getOrderNo();
        QueryWrapper<TwC2c> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        TwC2c one = this.getOne(queryWrapper);

        QueryWrapper<TwC2cBank> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("order_no", orderNo);
        TwC2cBank one1 = twC2cBankService.getOne(queryWrapper1);
        if(one1 == null){
            c2cBank.setType(1);
            c2cBank.setUid(one.getUid());
            c2cBank.setCreateTime(new Date());
            twC2cBankService.saveOrUpdate(c2cBank);
        }else{
            c2cBank.setId(one1.getId());
            c2cBank.setUid(one.getUid());
            twC2cBankService.updateById(c2cBank);
        }

        one.setStatus(1);
        this.updateById(one);

        return ResponseDTO.okMsg("分配卡号成功");
    }

    @Override
    public ResponseDTO getBankInfo(String orderno) {
        QueryWrapper<TwC2cBank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderno);
        TwC2cBank one = twC2cBankService.getOne(queryWrapper);
        return ResponseDTO.ok(one);
    }

    @Override
    public ResponseDTO cz(int uid, int country, BigDecimal num, int bankType,String language) {
        QueryWrapper<TwC2c> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("type",1);
        queryWrapper1.eq("uid",uid);
        queryWrapper1.eq("status", 1).or().eq("status", 4);
        TwC2c one = this.getOne(queryWrapper1);
        if(one != null){
            if(language != null){
                if(language.equals("zh")){
                    return ResponseDTO.userErrorParam("你还有支付订单未完成，去完成！");
                }else{
                    return ResponseDTO.userErrorParam("You still have unfinished payment orders, go and complete them.！");
                }
            }
        }

        QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id", uid);
        TwUser twUser = twUserService.getOne(queryWrapper2);

        QueryWrapper<TwArea> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("id", country);
        TwArea area = twAreaService.getOne(queryWrapper3);
        if(twUser != null){
            Integer companyId = twUser.getCompanyId();
            String userCode = twUser.getUserCode();
            String invit = twUser.getInvit();
            String path = twUser.getPath();
            Integer depatmentId = twUser.getDepatmentId();
            String username = twUser.getUsername();

            String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);
            TwC2c twC2c = new TwC2c();
            twC2c.setUid(uid);
            twC2c.setStatus(4);
            twC2c.setAgent(invit);
            twC2c.setPath(path);
            twC2c.setDepartment(depatmentId);
            twC2c.setUserCode(userCode);
            twC2c.setCompanyId(companyId);
            twC2c.setCountry(area.getNameZh());
            twC2c.setType(1);
            twC2c.setBankType(bankType);
            twC2c.setCreateTime(new Date());
            twC2c.setCzNum(num);
            twC2c.setDzNum(num);
            twC2c.setOrderNo(orderNo);
            twC2c.setUsername(username);
            this.saveOrUpdate(twC2c);
        }
        if(language.equals("zh")){
            return ResponseDTO.ok("正在匹配！");
        }else{
            return ResponseDTO.ok("matching！");
        }
    }

    @Override
    public ResponseDTO czImg(String orderNo,String img,String language) {
        QueryWrapper<TwC2c> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("order_no", orderNo);
        TwC2c one = this.getOne(queryWrapper2);
        if(one == null){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("订单不存在！");
            }else{
                return ResponseDTO.userErrorParam("Order does not exist！");
            }
        }
        one.setImg(img);
        this.updateById(one);
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO c2ctx(TwC2cBank twC2cBank) {
        String language = twC2cBank.getLanguage();
        QueryWrapper<TwC2c> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("type",2);
        queryWrapper1.eq("status", 1).or().eq("status", 4);
        TwC2c one = this.getOne(queryWrapper1);
        if(one != null){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("你还有支付订单未完成，去完成！");
            }else{
                return ResponseDTO.userErrorParam("You still have unfinished payment orders, go and complete them.！");
            }
        }

        QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_code", twC2cBank.getUserCode());
        TwUser twUser = twUserService.getOne(queryWrapper2);

        QueryWrapper<TwArea> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("id", twC2cBank.getCountryId());
        TwArea area = twAreaService.getOne(queryWrapper3);
        if(twUser != null){
            Integer companyId = twUser.getCompanyId();
            String userCode = twUser.getUserCode();
            String invit = twUser.getInvit();
            String path = twUser.getPath();
            Integer depatmentId = twUser.getDepatmentId();
            String username = twUser.getUsername();

            String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);
            TwC2c twC2c = new TwC2c();
            twC2c.setUid(twUser.getId());
            twC2c.setStatus(4);
            twC2c.setAgent(invit);
            twC2c.setPath(path);
            twC2c.setDepartment(depatmentId);
            twC2c.setUserCode(userCode);
            twC2c.setCompanyId(companyId);
            twC2c.setCountry(area.getNameZh());
            twC2c.setType(2);
            twC2c.setBankType(twC2cBank.getBankType());
            twC2c.setCreateTime(new Date());
            twC2c.setCzNum(twC2cBank.getNum());
            twC2c.setDzNum(twC2cBank.getNum());
            twC2c.setOrderNo(orderNo);
            twC2c.setUsername(username);
            this.saveOrUpdate(twC2c);

            twC2cBank.setType(2);
            twC2cBank.setUid(twUser.getId());
            twC2cBank.setCreateTime(new Date());
            twC2cBankService.saveOrUpdate(twC2cBank);
        }
             return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO<List<TwC2c>> czList(int type, int uid) {
        QueryWrapper<TwC2c> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("type", type);
        queryWrapper2.eq("uid", uid);
        queryWrapper2.orderByDesc("create_time");
        List<TwC2c> list = this.list(queryWrapper2);
        for (TwC2c twC2c:list){
            String orderNo = twC2c.getOrderNo();
            QueryWrapper<TwC2cBank> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("order_no", orderNo);
            twC2c.setTwC2cBank(twC2cBankService.getOne(queryWrapper3));
        }
        return ResponseDTO.ok(list);
    }

    @Override
    public ResponseDTO<TwC2c> info(String orderNo) {
        QueryWrapper<TwC2c> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("order_no", orderNo);
        TwC2c one = this.getOne(queryWrapper2);
        QueryWrapper<TwC2cBank> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("order_no", one.getOrderNo());
        one.setTwC2cBank(twC2cBankService.getOne(queryWrapper3));
        return ResponseDTO.ok(one);
    }
}




