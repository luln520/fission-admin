package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjprofitDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjprofitVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjprofitService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 矿机收益表(TwKjprofit)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:29
 */
@Service("twKjprofitService")
public class TwKjprofitServiceImpl extends ServiceImpl<TwKjprofitDao, TwKjprofit> implements TwKjprofitService {

    @Override
    public IPage<TwKjprofit> listpage(TwKjprofitVo twKjprofitVo) {
        Page<TwKjprofit> objectPage = new Page<>(twKjprofitVo.getPageNum(), twKjprofitVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twKjprofitVo));
        return objectPage;
    }
}
