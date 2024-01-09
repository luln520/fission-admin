package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwBillDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志(TwBill)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
@Service("twBillService")
public class TwBillServiceImpl extends ServiceImpl<TwBillDao, TwBill> implements TwBillService {

    @Override
    public IPage<TwBill> listpage(TwBillVo twBillVo) {
        Page<TwBill> objectPage = new Page<>(twBillVo.getPageNum(), twBillVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twBillVo));
        return objectPage;
    }

    @Override
    public List<TwBill> lists(int uid) {
        QueryWrapper<TwBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        // 按照 ID 倒序排列
        queryWrapper.orderByDesc("id");
        // 设置查询条数限制
        queryWrapper.last("LIMIT 50");

        // 调用 MyBatis-Plus 提供的方法进行查询
        return this.list(queryWrapper);
    }
}
