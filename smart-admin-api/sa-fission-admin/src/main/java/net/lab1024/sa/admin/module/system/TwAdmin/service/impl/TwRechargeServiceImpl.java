package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwOnlineDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwRechargeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.code.ErrorCode;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberService;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值记录(TwRecharge)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
@Service("twRechargeService")
@Transactional
@Slf4j
public class TwRechargeServiceImpl extends ServiceImpl<TwRechargeDao, TwRecharge> implements TwRechargeService {

    @Autowired
    private TwNoticeService twNoticeService;

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    @Lazy
    private TwUserService twUserService;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private TokenService tokenService;


    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private TwCompanyService twCompanyService;
    @Override
    public BigDecimal sumDayRecharge(String startTime, String endTime,int companyId) {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as dayRecharge")
                .ge("addtime", startTime)
                .le("addtime", endTime)
                .eq("status", 2)
                .eq("company_id", companyId);

        List<Map<String, Object>> result = this.baseMapper.selectMaps(queryWrapper);
        if (result.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject = result.get(0).get("dayRecharge");
        if (totalNumObject instanceof BigDecimal) {
            return ((BigDecimal) totalNumObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (totalNumObject instanceof Long) {
            return BigDecimal.valueOf((Long) totalNumObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (totalNumObject instanceof Integer) {
            return BigDecimal.valueOf((Integer) totalNumObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    @Override
    public int usersCount(String startTime, String endTime, int companyId) {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("addtime", startTime);
        queryWrapper.le("addtime", endTime);
        queryWrapper.eq("company_id", companyId);
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public int usersCountTotal(int companyId) {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", companyId);
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public BigDecimal sumAllRecharge(int companyId) {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as allRecharge")
                .eq("status", 2)
                .eq("company_id", companyId);
        List<Map<String, Object>> result = this.baseMapper.selectMaps(queryWrapper);
        if (result.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject = result.get(0).get("allRecharge");
        if (totalNumObject instanceof BigDecimal) {
            return ((BigDecimal) totalNumObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (totalNumObject instanceof Long) {
            return BigDecimal.valueOf((Long) totalNumObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (totalNumObject instanceof Integer) {
            return BigDecimal.valueOf((Integer) totalNumObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    @Override
    public IPage<TwRecharge> listpage(TwRechargeVo twRechargeVo, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwRecharge> objectPage = new Page<>(twRechargeVo.getPageNum(), twRechargeVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twRechargeVo));
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwRecharge> objectPage = new Page<>(twRechargeVo.getPageNum(), twRechargeVo.getPageSize());
                twRechargeVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twRechargeVo));
                return objectPage;
            }else{
                Page<TwRecharge> objectPage = new Page<>(twRechargeVo.getPageNum(), twRechargeVo.getPageSize());
                if(inviteType == 1){
                    twRechargeVo.setEmployeeId(byId.getEmployeeId());
                }
                objectPage.setRecords(baseMapper.listpage(objectPage, twRechargeVo));
                return objectPage;
            }
        }

        return null;
    }

    @Override
    public List<TwRecharge> listRecharge(int uid) {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        // 按照 ID 倒序排列
        queryWrapper.orderByDesc("id");
        // 设置查询条数限制
        queryWrapper.last("LIMIT 15");

        // 调用 MyBatis-Plus 提供的方法进行查询
        return this.list(queryWrapper);
    }

    @Override
    public ResponseDTO reject(int id) {

        try {
            QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            TwRecharge one = this.getOne(queryWrapper);
            if (one == null) {
                return ResponseDTO.userErrorParam("充币订单不存在");
            }

            if (one.getStatus() != 1) {
                return ResponseDTO.userErrorParam("此订单已处理");
            }

            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", one.getUid());
            TwUser twUser = twUserService.getOne(queryWrapper1);

            one.setUpdatetime(new Date());
            one.setStatus(3);
            this.updateById(one);
            TwNotice twNotice = new TwNotice();
            twNotice.setUid(one.getUid());
            twNotice.setAccount(one.getUsername());
            twNotice.setTitle("充币审核");
            twNotice.setTitleEn("Deposit review");
            twNotice.setContent("您的充币记录被系统驳回，请联系客服");
            twNotice.setContentEn("Your deposit record was rejected by the system, please contact customer service");
            twNotice.setAddtime(new Date());
            twNotice.setStatus(1);
            twNotice.setCompanyId(twUser.getCompanyId());
            twNotice.setDepartment(twUser.getDepatmentId());
            twNotice.setPath(twUser.getPath());
            twNoticeService.save(twNotice);
            return ResponseDTO.okMsg("充值驳回成功");
        } catch (Exception e) {
            return ResponseDTO.userErrorParam("充值驳回失败");
        }
    }

    @Override
    public ResponseDTO confirm(int id) {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwRecharge one = this.getOne(queryWrapper);
        if (one != null) {
            if(one.getStatus() == 2){
                return ResponseDTO.userErrorParam("订单已审核通过");
            }

            Integer uid = one.getUid();
            BigDecimal num = one.getNum();
            one.setUpdatetime(new Date());
            one.setStatus(2);
            this.updateById(one); //修改订单状态

            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", uid);
            TwUser twUser = twUserService.getOne(queryWrapper1);

            QueryWrapper<TwUserCoin> queryCoin = new QueryWrapper<>();
            queryCoin.eq("userid", uid);
            TwUserCoin twUserCoin = twUserCoinService.getOne(queryCoin);
            BigDecimal usdt = twUserCoin.getUsdt();
            if (twUserCoin != null) {
                twUserCoinService.incre(uid, num, usdt);//增加用户资产

                TwBill twBill = new TwBill();
                twBill.setUid(uid);
                twBill.setUsername(one.getUsername());
                twBill.setNum(one.getNum());
                twBill.setCompanyId(one.getCompanyId());
                twBill.setCoinname(one.getCoin());
                twBill.setAfternum(twUserCoinService.afternum(uid));
                twBill.setType(17);
                twBill.setAddtime(new Date());
                twBill.setSt(1);
                twBill.setRemark("充币到账");
                twBill.setDepartment(twUser.getDepatmentId());
                twBill.setPath(twUser.getPath());
                twBillService.save(twBill);

                TwNotice twNotice = new TwNotice();
                twNotice.setUid(one.getUid());
                twNotice.setAccount(one.getUsername());
                twNotice.setTitle("充币审核");
                twNotice.setTitleEn("Deposit review");
                twNotice.setContent("您的充值金额已到账，请注意查收");
                twNotice.setContentEn("Your recharge amount has arrived, please check it carefully");
                twNotice.setAddtime(new Date());
                twNotice.setStatus(1);
                twNotice.setCompanyId(one.getCompanyId());
                twNotice.setDepartment(twUser.getDepatmentId());
                twNotice.setPath(twUser.getPath());
                twNoticeService.save(twNotice);

                return ResponseDTO.okMsg("充值成功");
            }
        }
        return null;
    }

    @Override
    public boolean rechargeNum(Integer id, BigDecimal num) {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwRecharge one = this.getOne(queryWrapper);
        one.setNum(num);
        return this.updateById(one);
    }

    @Override
    public ResponseDTO paycoin(int uid, String coinname, String czaddress, String payimg, BigDecimal zznum, BigDecimal currenyNum,String currenyName,String czline,String  language,int companyId) {
            log.info("客户充值参数uid={},coinname={},czaddress={},payimg={},zznum={},czline={},language={},companyId={}",uid,coinname,czaddress,payimg,zznum,czline,language,companyId);
            QueryWrapper<TwUser> query = new QueryWrapper<>();
            query.eq("id", uid);
            TwUser one = twUserService.getOne(query);

            QueryWrapper<TwRecharge> rechargequery = new QueryWrapper<>();
            rechargequery.eq("uid", uid);
            rechargequery.eq("status", 1);
            TwRecharge twRech = this.getOne(rechargequery);
            if(twRech != null){
                if(language.equals("zh")){
                    return ResponseDTO.userErrorParam("凭证提交待审核");
                }else{
                    return ResponseDTO.userErrorParam("Voucher submitted for review");
                }
            }

            if(StringUtils.isEmpty(payimg)){
                if(language.equals("zh")){
                    return ResponseDTO.userErrorParam("请上传转账凭证图片");
                }else{
                    return ResponseDTO.userErrorParam("Please upload a picture of the transfer voucher");
                }
            }

            String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);
            TwRecharge twRecharge = new TwRecharge();
            twRecharge.setUid(uid);
            twRecharge.setUsername(one.getUsername());
            twRecharge.setCoin(coinname);
            twRecharge.setOrderNo(orderNo);
            twRecharge.setNum(zznum);
            twRecharge.setCurrenyNum(currenyNum);
            twRecharge.setCurrenyName(currenyName);
            twRecharge.setCompanyId(one.getCompanyId());
            twRecharge.setAddtime(new Date());
            twRecharge.setStatus(1);
            twRecharge.setUserCode(one.getUserCode());
            twRecharge.setPath(one.getPath());
            twRecharge.setDepartment(one.getDepatmentId());
            twRecharge.setPayimg(payimg);
            twRecharge.setAddress(czaddress);
            twRecharge.setCzline(czline);
            twRecharge.setMsg("");
            this.save(twRecharge);

            if(language.equals("zh")){
                return ResponseDTO.ok("凭证提交成功");
            }else{
                return ResponseDTO.ok("The voucher is successfully submitted");
            }

    }

}
