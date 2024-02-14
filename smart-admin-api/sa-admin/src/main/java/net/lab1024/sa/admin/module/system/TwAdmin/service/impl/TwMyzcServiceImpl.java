package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMyzcDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMyzcVo;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 提币表(TwMyzc)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
@Service("twMyzcService")
public class TwMyzcServiceImpl extends ServiceImpl<TwMyzcDao, TwMyzc> implements TwMyzcService {


    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private TwNoticeService twNoticeService;

    @Autowired
    private TwUserService twUserService;

    @Autowired
    private TwCoinService twCoinService;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Override
    public BigDecimal sumDayWithdraw(String startTime, String endTime) {
        QueryWrapper<TwMyzc> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as dayWithdraw")
                .ge("addtime", startTime)
                .le("addtime", endTime)
                .eq("status", 2);

        List<Map<String, Object>> result = this.baseMapper.selectMaps(queryWrapper);
        if (result.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject = result.get(0).get("dayWithdraw");
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
    public BigDecimal sumAllWithdraw() {
        QueryWrapper<TwMyzc> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as allWithdraw")
                .eq("status", 2);

        List<Map<String, Object>> result = this.baseMapper.selectMaps(queryWrapper);
        if (result.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject = result.get(0).get("allWithdraw");
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
    public IPage<TwMyzc> listpage(TwMyzcVo twMyzcVo, HttpServletRequest request) {
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getKey().equals("admin") || roleEmployeeVO.getKey().equals("backend")){
            Page<TwMyzc> objectPage = new Page<>(twMyzcVo.getPageNum(), twMyzcVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twMyzcVo));
            return objectPage;
        }

        if(roleEmployeeVO.getKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwMyzc> objectPage = new Page<>(twMyzcVo.getPageNum(), twMyzcVo.getPageSize());
                twMyzcVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twMyzcVo));
                return objectPage;
            }else{
                Page<TwMyzc> objectPage = new Page<>(twMyzcVo.getPageNum(), twMyzcVo.getPageSize());
                twMyzcVo.setEmployeeId(byId.getEmployeeId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twMyzcVo));
                return objectPage;
            }
        }

        return null;

    }

    @Override
    public List<TwMyzc> listPcpage(int uid) {
        QueryWrapper<TwMyzc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",uid);
        // 按照 ID 倒序排列
        queryWrapper.orderByDesc("id");
        // 设置查询条数限制
        queryWrapper.last("LIMIT 15");

        // 调用 MyBatis-Plus 提供的方法进行查询
        return this.list(queryWrapper);
    }

    @Override
    public ResponseDTO rejectCoin(int id) {
        try{
            QueryWrapper<TwMyzc> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            TwMyzc one = this.getOne(queryWrapper);
            if(one != null){
                if(one == null ){
                    return  ResponseDTO.userErrorParam("提币订单不存在");
                }

                if(one.getStatus() != 1){
                    return  ResponseDTO.userErrorParam("此订单已处理");
                }

                int uid = one.getUserid();
                String coinname = one.getCoinname();
                BigDecimal num = one.getNum();

                one.setEndtime(new Date());
                one.setStatus(3);
                this.updateById(one);

                QueryWrapper<TwUserCoin> queryCoin = new QueryWrapper<>();
                queryCoin.eq("userid", uid);
                TwUserCoin twUserCoin = twUserCoinService.getOne(queryCoin);
                if(twUserCoin != null){
//                    twUserCoinService.incre(uid,num,twUserCoin.getUsdt());//增加用户资产

                    QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("id", uid);
                    TwUser twUser = twUserService.getOne(queryWrapper2);

                    TwBill twBill = new TwBill();
                    twBill.setUid(uid);
                    twBill.setUserCode(twUser.getUserCode());
                    twBill.setUsername(one.getUsername());
                    twBill.setNum(num);
                    twBill.setPath(twUser.getPath());
                    twBill.setDepartment(twUser.getDepatmentId());
                    twBill.setCoinname(coinname);
                    twBill.setAfternum(twUserCoinService.afternum(uid));
                    twBill.setType(16);
                    twBill.setAddtime(new Date());
                    twBill.setSt(1);
                    twBill.setRemark("提币驳回，退回资金");
                    twBillService.save(twBill);

                    TwNotice twNotice = new TwNotice();
                    twNotice.setUid(uid);
                    twNotice.setAccount(one.getUsername());
                    twNotice.setTitle("提币审核");
                    twNotice.setTitleEn("Coin Withdrawal Review");
                    twNotice.setContent("您的提币申请被驳回，请联系管理员");
                    twNotice.setContentEn("Your coin withdrawal application has been rejected, please contact the administrator");
                    twNotice.setAddtime(new Date());
                    twNotice.setStatus(1);
                    twNotice.setPath(twUser.getPath());
                    twNotice.setDepartment(twUser.getDepatmentId());
                    twNoticeService.save(twNotice);
                    return ResponseDTO.okMsg("操作成功");
                }else {
                    return ResponseDTO.userErrorParam("操作失败");
                }
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
            QueryWrapper<TwMyzc> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            TwMyzc one = this.getOne(queryWrapper);
            if(one == null ){
                return  ResponseDTO.userErrorParam("充币订单不存在");
            }

            if(one.getStatus() != 1){
                return  ResponseDTO.userErrorParam("此订单已处理");
            }

            one.setEndtime(new Date());
            one .setStatus(2);
            this.updateById(one);

            QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", one.getUserid());
            TwUser twUser = twUserService.getOne(queryWrapper2);

            TwNotice twNotice = new TwNotice();
            twNotice.setUid(one.getUserid());
            twNotice.setAccount(one.getUsername());
            twNotice.setTitle("提币审核");
            twNotice.setTitleEn("Coin Withdrawal Review");
            twNotice.setContent("您的提币申请已通过，请及时查询");
            twNotice.setContentEn("Your currency withdrawal application has been approved, please check in time");
            twNotice.setAddtime(new Date());
            twNotice.setStatus(1);
            twNotice.setDepartment(twUser.getDepatmentId());
            twNotice.setPath(twUser.getPath());
            twNoticeService.save(twNotice);

            QueryWrapper<TwUserCoin> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("userid", twUser.getId());
            TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper3);

            twUserCoinService.decre(twUser.getId(),one.getNum(),twUserCoin.getUsdt());

            return ResponseDTO.okMsg("充值驳回成功");
        }catch (Exception e){
            return ResponseDTO.userErrorParam("充值驳回失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO tbhandle(int uid, int cid, String address, BigDecimal num,String language) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid);
        TwUser twUser = twUserService.getOne(queryWrapper);

        QueryWrapper<TwCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", cid);
        TwCoin twCoin = twCoinService.getOne(queryWrapper1);

        QueryWrapper<TwUserCoin> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("userid", uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper2);

        if(twUser.getRzstatus() != 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("请先完成实名认证!");
            }else{
                return ResponseDTO.userErrorParam("Please complete real-name authentication first！");
            }
        }

        if(twUser.getTxstate() == 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("已禁止提现!");
            }else{
                return ResponseDTO.userErrorParam("Withdrawal is prohibited！");
            }
        }

        if(num.compareTo(twCoin.getTxminnum()) < 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("不能低于最小提币值!");
            }else{
                return ResponseDTO.userErrorParam("Cannot be lower than the minimum withdrawal value！");
            }
        }

        if(num.compareTo(twCoin.getTxmaxnum())> 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("不能高于最大提币值!");
            }else{
                return ResponseDTO.userErrorParam("Cannot be higher than the maximum withdrawal value！");
            }
        }

        BigDecimal sxf = new BigDecimal(0);
        if(twCoin.getSxftype() == 1){
            MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
             sxf = num.multiply(twCoin.getTxsxf()).divide(new BigDecimal(100),mathContext);
        }

        if(twCoin.getSxftype() == 2) {
            sxf = twCoin.getTxsxfN();
        }

        if(sxf.compareTo(new BigDecimal(0)) == 0) {
            sxf = new BigDecimal(0);
        }

        BigDecimal tnum = num.subtract(sxf).setScale(2,RoundingMode.HALF_UP);
        if(twUserCoin.getUsdt().compareTo(tnum) < 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("账户余额不足!");
            }else{
                return ResponseDTO.userErrorParam("Insufficient account balance！");
            }
        }

        QueryWrapper<TwMyzc> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("userid", uid);
        queryWrapper3.eq("status", 1);
        TwMyzc one = this.getOne(queryWrapper3);
        if(one != null){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("有一笔订单未审核，请勿重复提交!");
            }else{
                return ResponseDTO.userErrorParam("There is an order that has not been reviewed. Please do not submit it again.！");
            }
        }
//        twUserCoinService.decre(uid,num,twUserCoin.getUsdt());

        String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);

        TwMyzc twMyzc = new TwMyzc();
        twMyzc.setUserid(uid);
        twMyzc.setOrderNo(orderNo);
        twMyzc.setUsername(twUser.getUsername());
        twMyzc.setUserCode(twUser.getUserCode());
        twMyzc.setCoinname(twCoin.getName());
        twMyzc.setNum(num);
        twMyzc.setAddress(address);
        twMyzc.setSort(1);
        twMyzc.setMum(tnum);
        twMyzc.setPath(twUser.getPath());
        twMyzc.setDepartment(twUser.getDepatmentId());
        twMyzc.setAddtime(new Date());
        twMyzc.setStatus(1);
        this.save(twMyzc);

        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(twUser.getUsername());
        twBill.setUserCode(twUser.getUserCode());
        twBill.setNum(num);
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setPath(twUser.getPath());
        twBill.setCoinname(twCoin.getName());
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(2);
        twBill.setAddtime(new Date());
        twBill.setSt(2);
        twBill.setRemark("提币申请");
        twBillService.save(twBill);
        return ResponseDTO.ok();
    }


}
