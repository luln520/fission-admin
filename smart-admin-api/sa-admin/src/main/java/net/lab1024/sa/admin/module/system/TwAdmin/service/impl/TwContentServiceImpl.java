package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwContentDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwContentService;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 公告内容(TwContent)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@Service("twContentService")
public class TwContentServiceImpl extends ServiceImpl<TwContentDao, TwContent> implements TwContentService {

    @Override
    public IPage<TwContent> listpage(PageParam pageParam) {
        Page<TwContent> objectPage = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, pageParam));
        return objectPage;
    }

    @Override
    public boolean addOrUpdate(TwContent twContent) {
        return this.saveOrUpdate(twContent);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }


}
