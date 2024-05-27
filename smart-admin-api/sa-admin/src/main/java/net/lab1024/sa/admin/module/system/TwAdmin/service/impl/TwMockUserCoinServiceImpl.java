package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMockUserCoinMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMockUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMockUserCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_mock_user_coin(模拟用户币种表)】的数据库操作Service实现
* @createDate 2024-06-02 15:18:54
*/
@Service
public class TwMockUserCoinServiceImpl extends ServiceImpl<TwMockUserCoinMapper, TwMockUserCoin>
    implements TwMockUserCoinService {

    @Autowired
    @Lazy
    private TwUserService twUserService;
    @Override
    public int incre(Integer uid, BigDecimal num, BigDecimal usdt) {
        return this.baseMapper.incre(uid,num,usdt);
    }

    public int decre(Integer uid, BigDecimal num,BigDecimal usdt) {
        return this.baseMapper.decre(uid,num,usdt);
    }

    @Override
    public BigDecimal afternum(Integer uid) {
        return this.baseMapper.afternum(uid);
    }

    @Override
    public BigDecimal sumUserCoin(int companyId) {
        return this.baseMapper.sumUserCoin(companyId);
    }

    @Override
    public IPage<TwMockUserCoin> listpage(TwUserVo twUserVo) {
        Page<TwMockUserCoin> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
        List<TwMockUserCoin> list = baseMapper.listpage(objectPage, twUserVo);
        for (TwMockUserCoin twMockUserCoin: list){
            Integer userid = twMockUserCoin.getUserid();
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", userid);
            TwUser one = twUserService.getOne(queryWrapper);
            if(one !=null){
                String username = one.getUsername();
                twMockUserCoin.setUsername(username);
            }
        }
        objectPage.setRecords(list);
        return objectPage;
    }

    @Override
    public TwMockUserCoin userCoin(int uid) {
        QueryWrapper<TwMockUserCoin> query = new QueryWrapper<>();
        query.eq("userid", uid);
        return this.getOne(query);
    }
}




