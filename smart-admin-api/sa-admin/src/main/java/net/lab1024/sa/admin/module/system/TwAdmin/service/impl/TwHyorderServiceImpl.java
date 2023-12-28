package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwFooterDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwFooter;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwFooterService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHyorderService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 合约订单表(TwHyorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
@Service("twHyorderService")
public class TwHyorderServiceImpl extends ServiceImpl<TwHyorderDao, TwHyorder> implements TwHyorderService {

    @Override
    public int countUnClosedOrders() {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1"); // 添加查询条件
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public TwHyorder hyorderId(int id) {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id); // 添加查询条件
        return this.getOne(queryWrapper);
    }

    @Override
    public IPage<TwHyorder> listpage(TwHyorderVo twHyorderVo) {
        Page<TwHyorder> objectPage = new Page<>(twHyorderVo.getPageNum(), twHyorderVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twHyorderVo));
        return objectPage;
    }
}
