package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjprofitDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKuangjiDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.TwPCKjprofitVo;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.DateUtil;
import net.lab1024.sa.common.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberService;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;

/**
 * 矿机列表(TwKuangji)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@Service("twKuangjiService")
public class TwKuangjiServiceImpl extends ServiceImpl<TwKuangjiDao, TwKuangji> implements TwKuangjiService {

    @Autowired
    private TwKjorderDao twKjorderDao;

    @Autowired
    private TwUserService twUserService;

    @Autowired
    private TwKjorderService twKjorderService;

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private TwKjprofitService twKjprofitService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwUserKuangjiService twUserKuangjiService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private TwCompanyService twCompanyService;
    @Override
    public IPage<TwKuangji> listpage(PageParam pageParam) {
        Page<TwKuangji> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public IPage<TwKuangji> pcList(PageParam pageParam, HttpServletRequest request) {

        //需要做token校验, 消息头的token优先于请求query参数的token
        String token = pageParam.getToken();
        List<TwKuangji> list = new ArrayList<>();
        if(token != null){
            Long uidToken = tokenService.getUIDToken(token);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",uidToken);
            TwUser user = twUserService.getOne(queryWrapper);

            Page<TwKuangji> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
            List<TwKuangji> twKuangjis = baseMapper.pcList(objectPage, pageParam);
            for(TwKuangji twKuangji:twKuangjis){
                Integer userid = user.getId();
                Integer kjid = twKuangji.getId();
                QueryWrapper<TwUserKuangji> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("kj_id", kjid);
                queryWrapper1.eq("user_id", userid);
                queryWrapper1.eq("company_id",user.getCompanyId());
                TwUserKuangji one = twUserKuangjiService.getOne(queryWrapper1);
                if(one == null){
                    TwUserKuangji twUserKuangji = new TwUserKuangji();
                    twUserKuangji.setMin(twKuangji.getPricemin());
                    twUserKuangji.setMax(twKuangji.getPricemax());
                    twUserKuangji.setNum(1);
                    twUserKuangji.setKjId(twKuangji.getId());
                    twUserKuangji.setKjName(twKuangji.getTitle());
                    twUserKuangji.setUserId(userid);
                    twUserKuangji.setCompanyId(user.getCompanyId());
                    twUserKuangji.setCreateTime(new Date());
                    twUserKuangjiService.saveOrUpdate(twUserKuangji);

                    twKuangji.setMin(twKuangji.getPricemin());
                    twKuangji.setMax(twKuangji.getPricemax());
                    twKuangji.setNum(1);
                    list.add(twKuangji);
                }else{
                    twKuangji.setMin(one.getMin());
                    twKuangji.setMax(one.getMax());
                    twKuangji.setNum(one.getNum());
                    list.add(twKuangji);
                }
            }
            objectPage.setRecords(list);
            return objectPage;
        }else{
            Page<TwKuangji> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
            List<TwKuangji> twKuangjis = baseMapper.pcList(objectPage, pageParam);
            for(TwKuangji twKuangji:twKuangjis){
                twKuangji.setMin(twKuangji.getPricemin());
                twKuangji.setMax(twKuangji.getPricemax());
                twKuangji.setNum(1);
                list.add(twKuangji);
            }
            objectPage.setRecords(list);
            return objectPage;
        }

    }

    @Override
    public TwKuangji detail(int id,int uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKuangji kuangji = this.getOne(queryWrapper);

        QueryWrapper<TwUserKuangji> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("kj_id", id);
        queryWrapper1.eq("user_id", uid);
        TwUserKuangji userKuangji = twUserKuangjiService.getOne(queryWrapper1);
        kuangji.setNum(userKuangji.getNum());
        kuangji.setMax(userKuangji.getMax());
        kuangji.setMin(userKuangji.getMin());
        return kuangji;
    }

    @Override
    public boolean addkj(TwKuangji twKuangji) {
        this.saveOrUpdate(twKuangji);

        QueryWrapper<TwUserKuangji> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("kj_id", twKuangji.getId());
        List<TwUserKuangji> list = twUserKuangjiService.list(queryWrapper);
        for(TwUserKuangji twUserKuang:list){
            twUserKuang.setMin(twKuangji.getPricemin());
            twUserKuang.setMax(twKuangji.getPricemax());
            twUserKuang.setKjName(twKuangji.getTitle());
            twUserKuang.setUpdateTime(new Date());
            twUserKuang.setCompanyId(twKuangji.getCompanyId());
            twUserKuangjiService.updateById(twUserKuang);
        }
        return true;
    }

    @Override
    public boolean open(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKuangji one = this.getOne(queryWrapper);
        one.setStatus(1);
        return this.updateById(one);
    }
    @Override
    public boolean close(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKuangji one = this.getOne(queryWrapper);
        one.setStatus(2);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
        QueryWrapper queryWrapper3 = new QueryWrapper();
        queryWrapper3.eq("kj_id", id); // 添加查询条件
        twUserKuangjiService.remove(queryWrapper3);

        return this.removeById(id);
    }

    @Override
    public ResponseDTO buyKuangji(int uid, int kid,BigDecimal buynum,String language) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",uid);
        TwUser user = twUserService.getOne(queryWrapper);
        Integer rzstatus = user.getRzstatus();
        if(rzstatus != 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("请先完成实名认证！");
            }else{
                return ResponseDTO.userErrorParam("Please complete real-name authentication first！");
            }
        }

        QueryWrapper queryKuaji = new QueryWrapper();
        queryKuaji.eq("id",kid);
        TwKuangji kuangji = this.getOne(queryKuaji);
         if(kuangji.getStatus() != 1){
             if(language.equals("zh")){
                 return ResponseDTO.userErrorParam("矿机暂停出售！");
             }else{
                 return ResponseDTO.userErrorParam("Mining machine sales suspended！");
             }
         }

        QueryWrapper<TwUserKuangji> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("user_id", user.getId()); // 添加查询条件
        queryWrapper3.eq("kj_id", kuangji.getId()); // 添加查询条件
        TwUserKuangji one = twUserKuangjiService.getOne(queryWrapper3);
                if(new BigDecimal(0).compareTo(one.getFloatMin()) == 0){
                    if(buynum.compareTo(one.getMin()) < 0){
                        if(language.equals("zh")){
                            return ResponseDTO.userErrorParam("投资金额不能小于最低投资额度！");
                        }else{
                            return ResponseDTO.userErrorParam("The investment amount cannot be less than the minimum investment amount！");
                        }
                    }
                }else{
                    if(buynum.compareTo(one.getFloatMin()) < 0){
                        if(language.equals("zh")){
                            return ResponseDTO.userErrorParam("此矿机没有配额，请稍后再试！");
                        }else{
                            return ResponseDTO.userErrorParam("This miner has no quota, please try again later!");
                        }
                    }
                }

        if(one.getMax().compareTo(buynum) < 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("投资金额不能高于最高投资额度！");
            }else{
                return ResponseDTO.userErrorParam("The investment amount cannot be higher than the maximum investment amount！");
            }
        }

        QueryWrapper queryKjorder = new QueryWrapper();
        queryKjorder.eq("uid",uid);
        queryKjorder.eq("kid",kuangji.getId());
        queryKjorder.eq("status",1);
        int count = twKjorderDao.selectCount(queryKjorder).intValue();
        if(count >= one.getNum()  ){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("该矿机无参与名额,请稍后再试！");
            }else{
                return ResponseDTO.userErrorParam("There is no quota for this mining machine, please try again later！");
            }
        }


        QueryWrapper queryUserCoin = new QueryWrapper();
        queryUserCoin.eq("userid",uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryUserCoin);

        BigDecimal usdt = twUserCoin.getUsdt();  //默认取usdt
        if(usdt.compareTo(buynum) < 0 ){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("账户余额不足！");
            }else{
                return ResponseDTO.userErrorParam("Insufficient account balance！");
            }
        }

        Integer companyId = user.getCompanyId();
        QueryWrapper<TwCompany> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id", companyId); // 添加查询条件
        TwCompany company = twCompanyService.getOne(queryWrapper2);
        BigDecimal kjFee = company.getKjFee();
        String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);
        //建仓矿机订单数据
        TwKjorder twKjorder = new TwKjorder();
        twKjorder.setKid(kuangji.getId());
        twKjorder.setUid(uid);
        twKjorder.setOrderNo(orderNo);
        twKjorder.setUsername(user.getUsername());
        twKjorder.setKjtitle(kuangji.getTitle());
        twKjorder.setImgs(kuangji.getImgs());
        twKjorder.setCompanyId(user.getCompanyId());
        twKjorder.setPath(user.getPath());
        twKjorder.setUserCode(user.getUserCode());
        twKjorder.setDepartment(user.getDepatmentId());
        twKjorder.setStatus(1);
        twKjorder.setBuynum(buynum);
        twKjorder.setCycle(kuangji.getCycle());
        twKjorder.setSynum(0);
//        twKjorder.setOuttype(kuangji.getOuttype());
        twKjorder.setOutcoin(kuangji.getOutcoin());
        MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal outnu = buynum.multiply(kuangji.getDayoutnum().divide(new BigDecimal(100),mathContext)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal outnum = outnu.subtract(kjFee);
        twKjorder.setOutnum(outnum);
//        twKjorder.setOutusdt(kuangji.getDayoutnum());
        twKjorder.setAddtime(new Date());
        twKjorder.setEndtime(addDay(new Date(),kuangji.getCycle()));
        long intaddtime = Instant.now().getEpochSecond();
        twKjorder.setIntaddtime((int) (intaddtime));
        Date date = addDay(new Date(),kuangji.getCycle());
        twKjorder.setIntendtime((int)(date.getTime()/1000));
        twKjorderService.save(twKjorder);

        //扣除会员额度
        twUserCoinService.decre(uid,buynum,twUserCoin.getUsdt());

        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(user.getUsername());
        twBill.setUserCode(user.getUserCode());
        twBill.setCompanyId(user.getCompanyId());
        twBill.setNum(buynum);
        twBill.setDepartment(user.getDepatmentId());
        twBill.setPath(user.getPath());
        twBill.setCoinname(kuangji.getOutcoin());
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(5);
        twBill.setAddtime(new Date());
        twBill.setSt(2);
        twBill.setRemark("购买矿机");
        twBillService.save(twBill);

        if(language.equals("zh")){
            return ResponseDTO.ok("购买成功！");
        }else{
            return ResponseDTO.ok("Purchase successful！");
        }
    }

    @Override
    public TwPCKjprofitVo kjprofitSum(int uid) {
        TwPCKjprofitVo twPCKjprofitVo = new TwPCKjprofitVo();
        QueryWrapper queryKjorder = new QueryWrapper();
        queryKjorder.eq("uid",uid);
        queryKjorder.eq("status",1);
        int count = twKjorderDao.selectCount(queryKjorder).intValue();
        twPCKjprofitVo.setCount(count);

        BigDecimal buynum = new BigDecimal(0);
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(buynum), 0) as buynum")
                .eq("uid", uid)
                .eq("status", 1);
        List<Map<String, Object>> result = twKjorderDao.selectMaps(queryWrapper);
        if (result.isEmpty()) {
            buynum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject = result.get(0).get("buynum");
        if (totalNumObject instanceof BigDecimal) {
            buynum = ((BigDecimal) totalNumObject).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject instanceof Long) {
            buynum = BigDecimal.valueOf((Long) totalNumObject).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject instanceof Integer) {
            buynum = BigDecimal.valueOf((Integer) totalNumObject).setScale(2, RoundingMode.HALF_UP);
        } else {
            // 处理其他可能的类型
            buynum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        twPCKjprofitVo.setBuynum(buynum);

        BigDecimal todaynum = new BigDecimal(0);
        String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd");
        QueryWrapper<TwKjorder> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("IFNULL(SUM(outnum), 0) as outnum")
                .eq("uid", uid)
                .eq("status", 1);
        List<Map<String, Object>> result1 = twKjorderDao.selectMaps(queryWrapper1);
        if (result1.isEmpty()) {
            todaynum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject1 = result1.get(0).get("outnum");
        if (totalNumObject1 instanceof BigDecimal) {
            todaynum = ((BigDecimal) totalNumObject1).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject1 instanceof Long) {
            todaynum = BigDecimal.valueOf((Long) totalNumObject1).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject1 instanceof Integer) {
            todaynum = BigDecimal.valueOf((Integer) totalNumObject1).setScale(2, RoundingMode.HALF_UP);
        } else {
            // 处理其他可能的类型
            todaynum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        twPCKjprofitVo.setTodaynum(todaynum);

        BigDecimal sumnum = new BigDecimal(0);
        QueryWrapper<TwKjprofit> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("uid",uid);
        List<TwKjprofit> sumlist = twKjprofitService.list(queryWrapper3);
        for(TwKjprofit twKjprofit:sumlist){
            sumnum = sumnum.add(twKjprofit.getNum());
        }
        twPCKjprofitVo.setSumnum(sumnum);

        return twPCKjprofitVo;
    }

    @Override
    public TwPCKjprofitVo kjprofitOneSum(int uid, int kid) {
        TwPCKjprofitVo twPCKjprofitVo = new TwPCKjprofitVo();
        BigDecimal buynum = new BigDecimal(0);
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(buynum), 0) as buynum")
                .eq("uid", uid)
                .eq("kid", kid)
                .eq("status", 1);
        List<Map<String, Object>> result = twKjorderDao.selectMaps(queryWrapper);
        if (result.isEmpty()) {
            buynum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject = result.get(0).get("buynum");
        if (totalNumObject instanceof BigDecimal) {
            buynum = ((BigDecimal) totalNumObject).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject instanceof Long) {
            buynum = BigDecimal.valueOf((Long) totalNumObject).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject instanceof Integer) {
            buynum = BigDecimal.valueOf((Integer) totalNumObject).setScale(2, RoundingMode.HALF_UP);
        } else {
            // 处理其他可能的类型
            buynum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        twPCKjprofitVo.setBuynum(buynum);

        return twPCKjprofitVo;
    }

    public Date addDay(Date date,int daysToAdd){

        // 将Date对象转换为Calendar对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 增加天数
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);

        // 获取增加天数后的Date对象
        return  calendar.getTime();
    }
}
