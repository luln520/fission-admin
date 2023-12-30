package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTyhyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户币种表(TwUserCoin)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@Service("twUserCoinService")
public class TwUserCoinServiceImpl extends ServiceImpl<TwUserCoinDao, TwUserCoin> implements TwUserCoinService {

    @Autowired
    private TwUserService twUserService;
    @Override
    public int incre(Integer uid, double num,String coinname) {
        return this.baseMapper.incre(uid,num,coinname);
    }

    public int decre(Integer uid, double num,String coinname) {
        return this.baseMapper.decre(uid,num,coinname);
    }

    @Override
    public double afternum(Integer uid, String coinname) {
        return this.baseMapper.afternum(uid,coinname);
    }

    @Override
    public IPage<TwUserCoin> listpage(TwUserVo twUserVo) {
        Page<TwUserCoin> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
        List<TwUserCoin> list = baseMapper.listpage(objectPage, twUserVo);
        for (TwUserCoin twUserCoin: list){
            Integer userid = twUserCoin.getUserid();
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", userid);
            TwUser one = twUserService.getOne(queryWrapper);

            String username = one.getUsername();

            twUserCoin.setUsername(username);
        }
        objectPage.setRecords(list);
        return objectPage;
    }
}
