package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwPledgeOrderMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_pledge_order(贷款订单表)】的数据库操作Service实现
* @createDate 2024-05-16 18:06:55
*/
@Service
public class TwPledgeOrderServiceImpl extends ServiceImpl<TwPledgeOrderMapper, TwPledgeOrder> implements TwPledgeOrderService {

    @Autowired
    @Lazy
    private TwUserService twUserService;


    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private TwNoticeService twNoticeService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Override
    public ResponseDTO reject(int id) {
        QueryWrapper<TwPledgeOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwPledgeOrder one = this.getOne(queryWrapper);
        if (one == null) {
            return ResponseDTO.userErrorParam("贷款订单不存在");
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
        twBill.setNum(one.getPledgeNum());
        twBill.setCoinname("USDT");
        twBill.setPath(twUser.getPath());
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setAfternum(twUserCoinService.afternum(twUser.getId()));
        twBill.setType(13);
        twBill.setAddtime(new Date());
        twBill.setRemark("借币审核驳回");
        twBillService.save(twBill);

        TwNotice twNotice = new TwNotice();
        twNotice.setUid(twUser.getId());
        twNotice.setAccount(twUser.getUsername());
        twNotice.setCompanyId(twUser.getCompanyId());
        twNotice.setTitle("借币审核驳回");
        twNotice.setTitleEn("Borrowing currency review rejected");
        twNotice.setContent("借币审核驳回，请联系客服!");
        twNotice.setContentEn("If the loan loan review is rejected, please contact customer service!");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNotice.setPath(twUser.getPath());
        twNotice.setDepartment(twUser.getDepatmentId());
        twNoticeService.save(twNotice);

        return ResponseDTO.okMsg("借币审核驳回成功");
    }

    @Override
    public ResponseDTO confirm(int id) {
        QueryWrapper<TwPledgeOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwPledgeOrder one = this.getOne(queryWrapper);
        if(one == null ){
            return  ResponseDTO.userErrorParam("借币订单不存在");
        }

        one .setStatus(2);
        this.updateById(one);

        QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_code", one.getUserCode());
        TwUser twUser = twUserService.getOne(queryWrapper2);

        QueryWrapper<TwUserCoin> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("userid", twUser.getId());
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper3);
        //减少资产
        twUserCoinService.decre(twUser.getId(), one.getPledgeNum(), twUserCoin.getUsdt());

        TwBill twBill = new TwBill();
        twBill.setUid(twUser.getId());
        twBill.setUserCode(twUser.getUserCode());
        twBill.setUsername(one.getUsername());
        twBill.setNum(one.getPledgeNum());
        twBill.setCoinname("USDT");
        twBill.setPath(twUser.getPath());
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setAfternum(twUserCoinService.afternum(twUser.getId()));
        twBill.setType(12);
        twBill.setAddtime(new Date());
        twBill.setRemark("借币审核成功");
        twBillService.save(twBill);

        TwNotice twNotice = new TwNotice();
        twNotice.setUid(twUser.getId());
        twNotice.setAccount(twUser.getUsername());
        twNotice.setCompanyId(twUser.getCompanyId());
        twNotice.setTitle("借币审核成功");
        twNotice.setTitleEn("Borrowing currency review successful");
        twNotice.setContent("借币审核成功，请注意查收!");
        twNotice.setContentEn("The borrowing currency review was successful, please check carefully!");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNotice.setPath(twUser.getPath());
        twNotice.setDepartment(twUser.getDepatmentId());
        twNoticeService.save(twNotice);

        return ResponseDTO.okMsg("操作成功");
    }

    @Override
    public ResponseDTO<List<TwPledgeOrder>> pledgeList(int uid) {
        QueryWrapper<TwPledgeOrder> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("uid", uid);
        queryWrapper2.orderByDesc("create_time");
        List<TwPledgeOrder> list = this.list(queryWrapper2);
        return ResponseDTO.ok(list);
    }

    @Override
    public ResponseDTO<TwPledgeOrder> info(String orderNo) {
        QueryWrapper<TwPledgeOrder> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("order_no", orderNo);
        TwPledgeOrder one = this.getOne(queryWrapper2);
        return ResponseDTO.ok(one);
    }

    @Override
    public ResponseDTO pledge(TwPledgeOrder twPledgeOrder) {
        String language = twPledgeOrder.getLanguage();
        QueryWrapper<TwPledgeOrder> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("uid",twPledgeOrder.getUid());
        queryWrapper1.eq("status",2);
        TwPledgeOrder one = this.getOne(queryWrapper1);
        if(one != null){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("你还有订单未完成，去完成！");
            }else{
                return ResponseDTO.userErrorParam("You still have unfinished orders, go and complete them.！");
            }
        }

        QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_code", twPledgeOrder.getUserCode());
        TwUser twUser = twUserService.getOne(queryWrapper2);

        if(twUser != null){
            Integer companyId = twUser.getCompanyId();
            String userCode = twUser.getUserCode();
            String path = twUser.getPath();
            Integer depatmentId = twUser.getDepatmentId();
            String username = twUser.getUsername();

            String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);
            twPledgeOrder.setUid(twUser.getId());
            twPledgeOrder.setStatus(1);
            twPledgeOrder.setPath(path);
            twPledgeOrder.setDepartment(depatmentId);
            twPledgeOrder.setUserCode(userCode);
            twPledgeOrder.setCompanyId(companyId);
            twPledgeOrder.setCreateTime(new Date());
            twPledgeOrder.setOrderNo(orderNo);
            twPledgeOrder.setUsername(username);
            this.saveOrUpdate(twPledgeOrder);

        }
        return ResponseDTO.ok();
    }

}




