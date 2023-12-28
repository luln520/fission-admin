package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKuangjiDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKuangjiService;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 矿机列表(TwKuangji)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@Service("twKuangjiService")
public class TwKuangjiServiceImpl extends ServiceImpl<TwKuangjiDao, TwKuangji> implements TwKuangjiService {

    @Override
    public IPage<TwKuangji> listpage(PageParam pageParam) {
        Page<TwKuangji> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public boolean addkj(TwKuangji twKuangji) {
       return this.saveOrUpdate(twKuangji);
    }

    @Override
    public boolean open(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKuangji one = this.getOne(queryWrapper);
        one.setStatus(1);
        return this.updateById(one);
    }
    @Override
    public boolean close(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKuangji one = this.getOne(queryWrapper);
        one.setStatus(2);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }
}
