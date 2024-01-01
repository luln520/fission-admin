package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMyzc2Dao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMyzcDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMyzcVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Override
    public BigDecimal sumDayWithdraw(String startTime, String endTime) {
        QueryWrapper<TwMyzc> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as dayWithdraw")
                .ge("startTime", startTime)
                .le("endTime", endTime)
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
    public IPage<TwMyzc> listpage(TwMyzcVo twMyzcVo) {
        Page<TwMyzc> objectPage = new Page<>(twMyzcVo.getPageNum(), twMyzcVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twMyzcVo));
        return objectPage;
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
                    twUserCoinService.incre(uid,num,coinname);//增加用户资产

                    TwBill twBill = new TwBill();
                    twBill.setUid(uid);
                    twBill.setUsername(one.getUsername());
                    twBill.setNum(num);
                    twBill.setCoinname(coinname);
                    twBill.setAfternum(twUserCoinService.afternum(uid,coinname));
                    twBill.setType(16);
                    twBill.setAddtime(new Date());
                    twBill.setSt(1);
                    twBill.setRemark("提币驳回，退回资金");
                    twBillService.save(twBill);

                    TwNotice twNotice = new TwNotice();
                    twNotice.setUid(uid);
                    twNotice.setAccount(one.getUsername());
                    twNotice.setTitle("提币审核");
                    twNotice.setContent("您的提币申请被驳回，请联系管理员");
                    twNotice.setAddtime(new Date());
                    twNotice.setStatus(1);
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

            TwNotice twNotice = new TwNotice();
            twNotice.setUid(one.getUserid());
            twNotice.setAccount(one.getUsername());
            twNotice.setTitle("提币审核");
            twNotice.setContent("您的提币申请已通过，请及时查询");
            twNotice.setAddtime(new Date());
            twNotice.setStatus(1);
            twNoticeService.save(twNotice);

            return ResponseDTO.okMsg("充值驳回成功");
        }catch (Exception e){
            return ResponseDTO.userErrorParam("充值驳回失败");
        }
    }


}
