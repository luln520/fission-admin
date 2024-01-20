package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import net.lab1024.sa.common.module.support.token.TokenService;
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

    @Override
    public BigDecimal sumDayRecharge(String startTime, String endTime) {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as dayRecharge")
                .ge("addtime", startTime)
                .le("addtime", endTime)
                .eq("status", 2);

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
    public BigDecimal sumAllRecharge() {
        QueryWrapper<TwRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as allRecharge")
                .eq("status", 2);
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

        if(roleEmployeeVO.getKey().equals("admin") || roleEmployeeVO.getKey().equals("backend")){
            Page<TwRecharge> objectPage = new Page<>(twRechargeVo.getPageNum(), twRechargeVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twRechargeVo));
            return objectPage;
        }

        if(roleEmployeeVO.getKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwRecharge> objectPage = new Page<>(twRechargeVo.getPageNum(), twRechargeVo.getPageSize());
                twRechargeVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twRechargeVo));
                return objectPage;
            }else{
                Page<TwRecharge> objectPage = new Page<>(twRechargeVo.getPageNum(), twRechargeVo.getPageSize());
                twRechargeVo.setEmployeeId(byId.getEmployeeId());
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
            Integer uid = one.getUid();
            String coinname = one.getCoin().toLowerCase();
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
                twNotice.setDepartment(twUser.getDepatmentId());
                twNotice.setPath(twUser.getPath());
                twNoticeService.save(twNotice);

                return ResponseDTO.okMsg("充值驳回成功");
            }
        }
        return null;
    }

    @Override
    public ResponseDTO paycoin(int uid, String coinname, String czaddress, String payimg, BigDecimal zznum, String czline) {
        try {
            QueryWrapper<TwUser> query = new QueryWrapper<>();
            query.eq("id", uid);
            TwUser one = twUserService.getOne(query);

            TwRecharge twRecharge = new TwRecharge();
            twRecharge.setUid(uid);
            twRecharge.setUsername(one.getUsername());
            twRecharge.setCoin(coinname);
            twRecharge.setNum(zznum);
            twRecharge.setAddtime(new Date());
            twRecharge.setStatus(1);
            twRecharge.setPath(one.getPath());
            twRecharge.setDepartment(one.getDepatmentId());
            twRecharge.setPayimg(payimg);
            twRecharge.setAddress(czaddress);
            twRecharge.setCzline(czline);
            twRecharge.setMsg("");
            this.save(twRecharge);

            return ResponseDTO.ok("凭证提交成功");
        } catch (Exception e) {
            return ResponseDTO.userErrorParam("凭证提交失败");
        }
    }

}
