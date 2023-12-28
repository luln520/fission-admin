package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwBbsettingDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwBillDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBbsetting;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBbsettingService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

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
}
