package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.api.client.util.Lists;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMcdHyorderMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMcdInfoMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMcdUserMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdInfo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMcdInfoService;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.util.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class TwMcdInfoServiceImpl extends ServiceImpl<TwMcdInfoMapper, TwMcdInfo> implements TwMcdInfoService {

    @Autowired
    private TwUserDao twUserDao;

    @Autowired
    private TwMcdHyorderMapper twMcdHyorderMapper;

    @Autowired
    private TwMcdUserMapper twMcdUserMapper;

    @Override
    public List<McdInfoVo> listMcdUser(int uid, String companyId) {
        List<McdInfoVo> mcdInfoVoList = Lists.newArrayList();

        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchandiser",1);
        queryWrapper.eq("user_type",1);
        queryWrapper.eq("company_id", companyId);
        List<TwUser> userList = twUserDao.selectList(queryWrapper);
        List<TwMcdInfo> followList = this.baseMapper.findFollowList(uid);
        if(!CollectionUtils.isEmpty(userList)) {
            for(TwUser twUser : userList) {
                int followCount = this.baseMapper.followCount(twUser.getId());
                QueryWrapper<TwMcdUser> mcdUserQueryWrapper = new QueryWrapper<>();
                mcdUserQueryWrapper.eq("uid",twUser.getId());
                TwMcdUser twMcdUser = twMcdUserMapper.selectOne(mcdUserQueryWrapper);

                McdInfoVo mcdInfoVo = new McdInfoVo();
                mcdInfoVo.setUid(twUser.getId());
                mcdInfoVo.setName(twUser.getUsername());
                mcdInfoVo.setFollowCount(followCount);

                if(twMcdUser != null) {
                    mcdInfoVo.setProfit(twMcdUser.getProfit());
                    mcdInfoVo.setMonthProfit(twMcdUser.getMonthProfit());
                    mcdInfoVo.setMonthProfitRate(twMcdUser.getMonthProfitRate());
                    mcdInfoVo.setTotalAmount(twMcdUser.getAmount());
                    mcdInfoVo.setDays(twMcdUser.getDays());
                    mcdInfoVo.setMinInvest(twMcdUser.getMinInvest());
                }
                mcdInfoVoList.add(mcdInfoVo);

                if(!CollectionUtils.isEmpty(followList)) {
                    for(TwMcdInfo twMcdInfo : followList) {
                        if(Objects.equals(twMcdInfo.getFollowUid(), twUser.getId())) mcdInfoVo.setFollow(true);
                    }
                }
            }
        }
        return mcdInfoVoList;
    }

    @Override
    public McdInfoVo queryMcdUser(int uid) {
        McdInfoVo mcdInfoVo = new McdInfoVo();
        TwUser twUser = twUserDao.selectById(uid);
        if(twUser != null) {
            int followCount = this.baseMapper.followCount(uid);
            QueryWrapper<TwMcdUser> mcdUserQueryWrapper = new QueryWrapper<>();
            mcdUserQueryWrapper.eq("uid", uid);
            TwMcdUser twMcdUser = twMcdUserMapper.selectOne(mcdUserQueryWrapper);

            mcdInfoVo.setUid(uid);
            mcdInfoVo.setName(twUser.getUsername());
            mcdInfoVo.setFollowCount(followCount);

            if (twMcdUser != null) {
                List<ProfitVo> profitVoList = new ArrayList<>();
                profitVoList = twMcdHyorderMapper.statisticProfit(twUser.getCompanyId(), uid);
                List<String> dateList = DateUtil.getPreviousDates(LocalDate.now(), 14);
                Map<String, BigDecimal> profitMap = profitVoList.stream()
                        .collect(Collectors.toMap(ProfitVo::getDate, ProfitVo::getProfit));

                List<ProfitVo> result = new ArrayList<>();
                for (String date : dateList) {
                    ProfitVo vo = new ProfitVo();
                    vo.setDate(date);
                    vo.setProfit(profitMap.getOrDefault(date, BigDecimal.ZERO));
                    result.add(vo);
                }


                mcdInfoVo.setProfit(twMcdUser.getProfit());
                mcdInfoVo.setMonthProfit(twMcdUser.getMonthProfit());
                mcdInfoVo.setMonthProfitRate(twMcdUser.getMonthProfitRate());
                mcdInfoVo.setTotalAmount(twMcdUser.getAmount());
                mcdInfoVo.setDays(twMcdUser.getDays());
                mcdInfoVo.setMinInvest(twMcdUser.getMinInvest());
                mcdInfoVo.setProfitHistory(result);
            }
        }

        return mcdInfoVo;
    }

    @Override
    public McdUserInfoVo queryUserInfo(int uid) {
        McdUserInfoVo mcdUserInfoVo = new McdUserInfoVo();
        List<TwMcdInfo> twMcdInfoList = this.baseMapper.findFollowList(uid);
        mcdUserInfoVo.setStarCount(CollectionUtils.isEmpty(twMcdInfoList)? 0 : twMcdInfoList.size());
        BigDecimal totalAmount = twMcdHyorderMapper.totalAmount(uid);
        mcdUserInfoVo.setAmount(totalAmount);
        BigDecimal totalPloss = twMcdHyorderMapper.totalPloss(uid);
        mcdUserInfoVo.setPloss(totalPloss);
        mcdUserInfoVo.setProfitRate(totalPloss.divide(totalAmount).setScale(2, RoundingMode.HALF_UP));
        return mcdUserInfoVo;
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
        queryWrapper.eq("follow_uid", followUid);
        queryWrapper.eq("uid",  uid);
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
        queryWrapper.eq("follow_uid", followUid);
        queryWrapper.eq("uid", uid);
        TwMcdInfo twMcdInfo = this.baseMapper.selectOne(queryWrapper);
        if(twMcdInfo != null) {
            twMcdInfo.setStatus(0);
            twMcdInfo.setUpdateTime(new Date());
            this.baseMapper.updateById(twMcdInfo);
        }
    }

    @Override
    public void applyMcd(int uid) {
        QueryWrapper<TwMcdUser> mcdUserQueryWrapper = new QueryWrapper<>();
        mcdUserQueryWrapper.eq("uid", uid);
        TwMcdUser twMcdUser = twMcdUserMapper.selectOne(mcdUserQueryWrapper);
        if(twMcdUser == null) {
            twMcdUser = new TwMcdUser();
            twMcdUser.setUid(uid);
            twMcdUserMapper.insert(twMcdUser);
        }else {
            twMcdUser.setStatus(0);
            twMcdUserMapper.updateById(twMcdUser);
        }
    }

    @Override
    public IPage<TwMcdUser> listpage(PageParam pageParam) {
        Page<TwMcdUser> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(this.twMcdUserMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public void approveMcd(int id) {
        TwMcdUser twMcdUser = twMcdUserMapper.selectById(id);
        if(twMcdUser != null) {
            twMcdUser.setStatus(1);
            twMcdUserMapper.updateById(twMcdUser);

            TwUser twUser = twUserDao.selectById(twMcdUser.getUid());
            twUser.setMerchandiser(1);
            twUserDao.updateById(twUser);
        }
    }

    @Override
    public void rejectMcd(int id) {
        TwMcdUser twMcdUser = twMcdUserMapper.selectById(id);
        if(twMcdUser != null) {
            twMcdUser.setStatus(2);
            twMcdUserMapper.updateById(twMcdUser);
        }
    }

    @Override
    public void updateMcdUser(TwMcdUser twMcdUser) {
        TwMcdUser destTwMcdUser = twMcdUserMapper.selectById(twMcdUser.getId());
        if(destTwMcdUser != null) {
            try {
                BeanUtils.copyProperties(destTwMcdUser, twMcdUser);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            twMcdUserMapper.updateById(destTwMcdUser);
        }
    }
}
