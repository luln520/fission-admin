package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNewsType;
import net.lab1024.sa.common.common.domain.PageParam;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_news_type(新闻分类)】的数据库操作Service
* @createDate 2024-01-12 17:03:04
*/
public interface TwNewsTypeService extends IService<TwNewsType> {
    List<TwNewsType> listpage();

    List<TwNewsType> listType();

    boolean addOrUpdate(TwNewsType twNewsType);

    boolean delete(int id);

    TwNewsType find(int id);
}
