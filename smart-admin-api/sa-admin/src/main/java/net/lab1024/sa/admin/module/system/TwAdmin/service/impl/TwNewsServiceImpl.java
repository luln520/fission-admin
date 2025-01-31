package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwNewsMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_news(新闻)】的数据库操作Service实现
* @createDate 2024-01-12 17:02:15
*/
@Service
public class TwNewsServiceImpl extends ServiceImpl<TwNewsMapper, TwNews>
    implements TwNewsService {

    @Override
    public IPage<TwNews> listpage(TwNewsVo twNewsVo) {
        Page<TwNews> objectPage = new Page<>(twNewsVo.getPageNum(), twNewsVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twNewsVo));
        return objectPage;
    }

    @Override
    public boolean addOrUpdate(TwNews twNews) {
        twNews.setCreateTime(new Date());
        return this.saveOrUpdate(twNews);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public TwNews find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<TwNews> listType(int companyId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("company_id",companyId);
        return this.list(queryWrapper);
    }

}




