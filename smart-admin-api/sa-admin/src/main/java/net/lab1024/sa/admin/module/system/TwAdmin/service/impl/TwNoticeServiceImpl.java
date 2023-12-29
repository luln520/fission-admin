package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMyzcDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwNoticeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMyzcService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 通知表(TwNotice)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
@Service("twNoticeService")
public class TwNoticeServiceImpl extends ServiceImpl<TwNoticeDao, TwNotice> implements TwNoticeService {

    @Override
    public IPage<TwNotice> listpage(PageParam pageParam) {
        Page<TwNotice> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }
}
