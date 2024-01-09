package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjorderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 矿机订单表(TwKjorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
@Service("twKjorderService")
public class TwKjorderServiceImpl extends ServiceImpl<TwKjorderDao, TwKjorder> implements TwKjorderService {

    @Override
    public IPage<TwKjorder> listpage(TwKjorderVo twKjorderVo) {

            Page<TwKjorder> objectPage = new Page<>(twKjorderVo.getPageNum(), twKjorderVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twKjorderVo));
            return objectPage;
    }

    @Override
    public List<TwKjorder> uidList(int uid) {
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid); // 添加查询条件
        return this.list(queryWrapper);
    }

    @Override
    public int countAllOrders() {
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1"); // 添加查询条件
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public boolean open(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKjorder one = this.getOne(queryWrapper);
        one.setStatus(1);
        return this.updateById(one);
    }
    @Override
    public boolean close(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        TwKjorder one = this.getOne(queryWrapper);
        one.setStatus(2);
        return this.updateById(one);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }
}
