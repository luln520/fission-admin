package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAdminLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinCommentDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinCommentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 后台管理员操作日志表(TwAdminLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:17:09
 */
@Service("twAdminLogService")
public class TwAdminLogServiceImpl extends ServiceImpl<TwAdminLogDao, TwAdminLog> implements TwAdminLogService {

    @Override
    public IPage<TwAdminLog> listpage(TwBillVo twBillVo) {
        Page<TwAdminLog> objectPage = new Page<>(twBillVo.getPageNum(), twBillVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twBillVo));
        return objectPage;
    }

    @Override
    public boolean add(TwAdminLog twAdminLog) {
        return this.save(twAdminLog);
    }
}
