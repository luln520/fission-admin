package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 币种配置表(TwCoin)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
@Service("twCoinService")
public class TwCoinServiceImpl extends ServiceImpl<TwCoinDao, TwCoin> implements TwCoinService {



    @Override
    public IPage<TwCoin> listpage(PageParam pageParam) {
        Page<TwCoin> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public List<TwCoin> lists( int companyId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("txstatus",1);
        queryWrapper.eq("company_id",companyId);
        return this.list(queryWrapper);
    }

    @Override
    public boolean addOrUpdate(TwCoin twCoin) {
        return this.saveOrUpdate(twCoin);
    }

    @Override
    public boolean updateStatus(int id, Integer status) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwCoin one = this.getOne(queryWrapper);
        one.setStatus(status);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public TwCoin find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }
}
