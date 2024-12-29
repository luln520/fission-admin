package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.api.client.util.Lists;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMcdInfoMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMcdUserMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdInfo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.FollowVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.McdInfoVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMcdInfoService;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TwMcdInfoServiceImpl extends ServiceImpl<TwMcdInfoMapper, TwMcdInfo> implements TwMcdInfoService {

    @Autowired
    private TwUserDao twUserDao;

    @Autowired
    private TwMcdUserMapper twMcdUserMapper;

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
                QueryWrapper<TwMcdUser> mcdUserQueryWrapper = new QueryWrapper<>();
                mcdUserQueryWrapper.eq("uid",twUser.getId());
                TwMcdUser twMcdUser = twMcdUserMapper.selectOne(mcdUserQueryWrapper);

                McdInfoVo mcdInfoVo = new McdInfoVo();
                mcdInfoVo.setUid(twUser.getId());
                mcdInfoVo.setName(twUser.getUsername());
                mcdInfoVo.setFollowCount(followCount);

                if(twMcdUser != null) {
                    mcdInfoVo.setProfit(twMcdUser.getProfit());
                    mcdInfoVo.setProfitRate(twMcdUser.getMonthProfitRate());
                    mcdInfoVo.setTotalAmount(twMcdUser.getAmount());
                }
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
