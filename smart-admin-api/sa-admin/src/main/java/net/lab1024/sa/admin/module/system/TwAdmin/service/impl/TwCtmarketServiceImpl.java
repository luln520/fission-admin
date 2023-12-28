package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCtmarketDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCtmarketService;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 合约交易对配置(TwCtmarket)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
@Service("twCtmarketService")
public class TwCtmarketServiceImpl extends ServiceImpl<TwCtmarketDao, TwCtmarket> implements TwCtmarketService {

    @Override
    public IPage<TwCtmarket> listpage(PageParam pageParam) {
        Page<TwCtmarket> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public boolean addOrUpdate(TwCtmarket twCtmarket) {
       return  this.saveOrUpdate(twCtmarket);
    }

    @Override
    public boolean updateStatus(int id, int status) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwCtmarket one = this.getOne(queryWrapper);
        one.setStatus(status);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
         return this.removeById(id);
    }
}
