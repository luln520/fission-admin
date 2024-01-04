package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjprofitDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKuangjiDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 矿机列表(TwKuangji)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@Service("twKuangjiService")
public class TwKuangjiServiceImpl extends ServiceImpl<TwKuangjiDao, TwKuangji> implements TwKuangjiService {

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
    public ResponseDTO buyKuangji(int uid, int kid) {
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
         if(kuangji.getSellnum() + kuangji.getYcnum() >= kuangji.getAllnum()){
             return ResponseDTO.userErrorParam("矿机已售罄");
         }
        QueryWrapper queryKjorder = new QueryWrapper();
        queryKjorder.eq("kid",kid);
        queryKjorder.eq("uid",kid);
        queryKjorder.eq("status",1);
        int count = twKjprofitDao.selectCount(queryKjorder).intValue();
        if(count >= kuangji.getBuymax()){
             return ResponseDTO.userErrorParam("已达到限购数量");
        }

        QueryWrapper queryUserCoin = new QueryWrapper();
        queryUserCoin.eq("userid",uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryUserCoin);

        BigDecimal usdt = twUserCoin.getUsdt();  //默认取usdt
        BigDecimal pricenum = kuangji.getPricenum();
        if(usdt.compareTo(pricenum) < 0 ){
            return ResponseDTO.userErrorParam("账户余额不足");
        }

        //建仓矿机订单数据
        TwKjorder twKjorder = new TwKjorder();
        twKjorder.setKid(kuangji.getId());
        twKjorder.setType(1);
        twKjorder.setSharebl(0.0);
        twKjorder.setUid(uid);
        twKjorder.setUsername(user.getUsername());
        twKjorder.setKjtitle(kuangji.getTitle());
        twKjorder.setImgs(kuangji.getImgs());
        twKjorder.setStatus(1);
        twKjorder.setCycle(kuangji.getCycle());
        twKjorder.setSynum(kuangji.getCycle());
//        twKjorder.setOuttype(kuangji.getOuttype());
//        twKjorder.setOutcoin(kuangji.getOutcoin());
        twKjorder.setOutnum(kuangji.getDayoutnum());
//        twKjorder.setOutusdt(kuangji.getDayoutnum());
        twKjorder.setAddtime(new Date());
        twKjorder.setEndtime(addDay(new Date()));
        twKjorder.setIntaddtime((int) new Date().getTime()/1000);
        twKjorder.setIntendtime((int) addDay(new Date()).getTime()/1000);
        twKjorderService.save(twKjorder);

        //扣除会员额度
        twUserCoinService.decre(uid,pricenum,twUserCoin.getUsdt());

        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(user.getUsername());
        twBill.setNum(pricenum);
        twBill.setCoinname(kuangji.getOutcoin());
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(5);
        twBill.setAddtime(new Date());
        twBill.setSt(2);
        twBill.setRemark("购买矿机");
        twBillService.save(twBill);

        return ResponseDTO.okMsg("购买成功");
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
