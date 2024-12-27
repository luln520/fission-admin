package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.api.client.util.Lists;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMcdInfoMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdInfo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.FollowVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.McdInfoVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMcdInfoService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TwMcdInfoServiceImpl extends ServiceImpl<TwMcdInfoMapper, TwMcdInfo> implements TwMcdInfoService {

    @Autowired
    private TwUserDao twUserDao;

    @Override
    public List<McdInfoVo> listMcdUser(String companyId) {
        List<McdInfoVo> mcdInfoVoList = Lists.newArrayList();

        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchandiser",1);
        queryWrapper.eq("user_type",1);
        queryWrapper.eq("company_id", companyId);
        List<TwUser> userList = twUserDao.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(userList)) {
            for(TwUser twUser : userList) {
                int followCount = this.baseMapper.followCount(twUser.getId());
                BigDecimal totalAmount = this.baseMapper.totalAmount(twUser.getId());
                BigDecimal totalProfit = this.baseMapper.totalProfit(twUser.getId());

                McdInfoVo mcdInfoVo = new McdInfoVo();
                mcdInfoVo.setUid(twUser.getId());
                mcdInfoVo.setName(twUser.getUsername());
                mcdInfoVo.setFollowCount(followCount);
                mcdInfoVo.setProfit(totalProfit);
                mcdInfoVo.setProfitRate(totalProfit.divide(totalAmount, 2, RoundingMode.HALF_UP));
                mcdInfoVo.setTotalAmount(totalAmount);

                mcdInfoVoList.add(mcdInfoVo);
            }
        }
        return mcdInfoVoList;
    }

    private int betweenDays(Date before) {
        long diffInMillies = Math.abs(new Date().getTime() - before.getTime());
        long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
        return (int) diffInDays;
    }

    @Override
    public List<FollowVo> listMyFollow(int uid) {
        List<FollowVo> followVoList = Lists.newArrayList();
        List<TwUser> userList = twUserDao.listMcdUser(uid);
        if(!CollectionUtils.isEmpty(userList)) {
            for (TwUser twUser : userList) {
                TwMcdInfo twMcdInfo = this.baseMapper.find(uid, twUser.getId());
                FollowVo followVo = new FollowVo();
                followVo.setUid(twUser.getId());
                followVo.setName(twUser.getUsername());
                followVo.setDay(betweenDays(twMcdInfo.getCreateTime()));
                followVo.setProfit(twMcdInfo.getProfit());
                followVo.setDate(twMcdInfo.getCreateTime());

                followVoList.add(followVo);
            }
        }
        return followVoList;
    }

    @Override
    public void addFollow(int followUid, int uid, BigDecimal investProp) {
        QueryWrapper<TwMcdInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follow_uid",1);
        queryWrapper.eq("uid",1);
        TwMcdInfo twMcdInfo = this.baseMapper.selectOne(queryWrapper);
        if(twMcdInfo != null) {
            twMcdInfo.setStatus(1);
            twMcdInfo.setInvestProp(investProp);
            twMcdInfo.setUpdateTime(new Date());
            this.baseMapper.updateById(twMcdInfo);
        }else {
            twMcdInfo = new TwMcdInfo();
            twMcdInfo.setUid(uid);
            twMcdInfo.setFollowUid(followUid);
            twMcdInfo.setInvestProp(investProp);
            twMcdInfo.setStatus(1);
            this.baseMapper.insert(twMcdInfo);
        }
    }

    @Override
    public void delFollow(int followUid, int uid) {
        QueryWrapper<TwMcdInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("follow_uid",1);
        queryWrapper.eq("uid",1);
        TwMcdInfo twMcdInfo = this.baseMapper.selectOne(queryWrapper);
        if(twMcdInfo != null) {
            twMcdInfo.setStatus(0);
            twMcdInfo.setUpdateTime(new Date());
            this.baseMapper.updateById(twMcdInfo);
        }
    }
}
