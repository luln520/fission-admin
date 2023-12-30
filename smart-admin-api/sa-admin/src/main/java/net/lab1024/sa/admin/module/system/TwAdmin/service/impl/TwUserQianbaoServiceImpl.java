package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserQianbaoDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserQianbao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserQianbaoService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户钱包表(TwUserQianbao)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@Service("twUserQianbaoService")
public class TwUserQianbaoServiceImpl extends ServiceImpl<TwUserQianbaoDao, TwUserQianbao> implements TwUserQianbaoService {

    @Autowired
    private TwUserService twUserService;
    @Autowired
    private TwCoinService twCoinService;

    @Override
    public IPage<TwUserQianbao> listpage(TwUserVo twUserVo) {
            Page<TwUserQianbao> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twUserVo));
            return objectPage;
        }

    @Override
    public List<TwUserQianbao> lists(int uid) {
        QueryWrapper<TwUserQianbao> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", uid);
        return this.list(queryWrapper);
    }

    @Override
    public boolean addUpdate(TwUserQianbao userQianBao) {
        return this.saveOrUpdate(userQianBao);
    }

    @Override
    public boolean del(int id) {
        return this.removeById(id);
    }

    @Override
    public boolean add(int uid, int oid, String address, String remark, String czline) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid);
        TwUser twUser = twUserService.getOne(queryWrapper);

        QueryWrapper<TwCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", uid);
        TwCoin twCoin = twCoinService.getOne(queryWrapper1);

        TwUserQianbao twUserQianbao = new TwUserQianbao();
        twUserQianbao.setUserid(uid);
        twUserQianbao.setCoinname(twUser.getUsername());
        twUserQianbao.setCzline(czline);
        twUserQianbao.setName(twCoin.getName());
        twUserQianbao.setRemark(remark);
        twUserQianbao.setAddr(address);
        twUserQianbao.setSort(1);
        twUserQianbao.setAddtime(new Date());
        twUserQianbao.setStatus(1);
        this.save(twUserQianbao);
        return true;
    }
}
