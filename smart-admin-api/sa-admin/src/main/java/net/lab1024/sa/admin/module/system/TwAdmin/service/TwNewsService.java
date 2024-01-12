package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.common.common.domain.PageParam;

/**
* @author 1
* @description 针对表【tw_news(新闻)】的数据库操作Service
* @createDate 2024-01-12 17:02:15
*/
public interface TwNewsService extends IService<TwNews> {

    IPage<TwNews> listpage(PageParam pageParam);

    boolean addOrUpdate(TwNews twNews);

    boolean delete(int id);

    TwNews find(int id);
}
