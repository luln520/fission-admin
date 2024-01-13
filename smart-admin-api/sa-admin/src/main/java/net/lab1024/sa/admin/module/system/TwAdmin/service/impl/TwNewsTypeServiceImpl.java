package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwNewsTypeMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNewsType;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsTypeService;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_news_type(新闻分类)】的数据库操作Service实现
* @createDate 2024-01-12 17:03:04
*/
@Service
public class TwNewsTypeServiceImpl extends ServiceImpl<TwNewsTypeMapper, TwNewsType>
    implements TwNewsTypeService {

    @Override
    public List<TwNewsType> listpage() {
        return this.list();
    }

    @Override
    public List<TwNewsType> listType() {
        return this.list();
    }

    @Override
    public boolean addOrUpdate(TwNewsType twNewsType) {
        return this.saveOrUpdate(twNewsType);
    }

    @Override
    public boolean delete(int id) {
        return this.removeById(id);
    }

    @Override
    public TwNewsType find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return this.getOne(queryWrapper);
    }

}




