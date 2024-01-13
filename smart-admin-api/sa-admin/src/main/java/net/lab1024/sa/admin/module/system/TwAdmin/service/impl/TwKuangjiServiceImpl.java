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
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private TwKjprofitDao twKjprofitDao;

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
    @Override
    public IPage<TwKuangji> listpage(PageParam pageParam) {
        Page<TwKuangji> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public IPage<TwKuangji> pcList(PageParam pageParam) {
        Page<TwKuangji> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.pcList(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public TwKuangji detail(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean addkj(TwKuangji twKuangji) {

       return this.saveOrUpdate(twKuangji);
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
        return this.removeById(id);
    }

    @Override
    public ResponseDTO buyKuangji(int uid, int kid,BigDecimal buynum) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",uid);
        TwUser user = twUserService.getOne(queryWrapper);
        Integer rzstatus = user.getRzstatus();
        if(rzstatus != 2){
            return ResponseDTO.userErrorParam("请先完成实名认证");
        }

        QueryWrapper queryKuaji = new QueryWrapper();
        queryKuaji.eq("id",kid);
        TwKuangji kuangji = this.getOne(queryKuaji);
         if(kuangji.getStatus() != 1){
             return ResponseDTO.userErrorParam("矿机暂停出售");
         }


        QueryWrapper queryUserCoin = new QueryWrapper();
        queryUserCoin.eq("userid",uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryUserCoin);

        BigDecimal usdt = twUserCoin.getUsdt();  //默认取usdt
        if(usdt.compareTo(buynum) < 0 ){
            return ResponseDTO.userErrorParam("账户余额不足");
        }

        //建仓矿机订单数据
        TwKjorder twKjorder = new TwKjorder();
        twKjorder.setKid(kuangji.getId());
        twKjorder.setUid(uid);
        twKjorder.setUsername(user.getUsername());
        twKjorder.setKjtitle(kuangji.getTitle());
        twKjorder.setImgs(kuangji.getImgs());
        twKjorder.setPath(user.getPath());
        twKjorder.setDepartment(user.getDepatmentId());
        twKjorder.setStatus(1);
        twKjorder.setBuynum(buynum);
        twKjorder.setCycle(kuangji.getCycle());
        twKjorder.setSynum(kuangji.getCycle());
//        twKjorder.setOuttype(kuangji.getOuttype());
        twKjorder.setOutcoin(kuangji.getOutcoin());
        MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal outnum = buynum.multiply(kuangji.getDayoutnum().divide(new BigDecimal(100),mathContext)).setScale(2, RoundingMode.HALF_UP);
        twKjorder.setOutnum(outnum);
//        twKjorder.setOutusdt(kuangji.getDayoutnum());
        twKjorder.setAddtime(new Date());
        twKjorder.setEndtime(addDay(new Date()));
        long intaddtime = Instant.now().getEpochSecond();
        twKjorder.setIntaddtime((int) (intaddtime));
        Date date = addDay(new Date());
        twKjorder.setIntendtime((int)(date.getTime()/1000));
        twKjorderService.save(twKjorder);

        //扣除会员额度
        twUserCoinService.decre(uid,buynum,twUserCoin.getUsdt());

        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(user.getUsername());
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

        return ResponseDTO.okMsg("购买成功");
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
        QueryWrapper<TwKjprofit> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("IFNULL(SUM(num), 0) as num")
                .eq("uid", uid)
                .eq("day", nowdate);
        List<Map<String, Object>> result1 = twKjprofitDao.selectMaps(queryWrapper1);
        if (result1.isEmpty()) {
            todaynum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject1 = result1.get(0).get("num");
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

    public Date addDay(Date date){

        // 将Date对象转换为Calendar对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 增加天数
        int daysToAdd = 5;
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);

        // 获取增加天数后的Date对象
        return  calendar.getTime();
    }
}
