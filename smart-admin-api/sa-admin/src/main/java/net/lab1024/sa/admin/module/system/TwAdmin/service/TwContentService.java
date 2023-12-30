package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 公告内容(TwContent)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
public interface TwContentService extends IService<TwContent> {

    IPage<TwContent> listpage(PageParam pageParam);

    IPage<TwContent> listPCpage(PageParam pageParam);

    boolean addOrUpdate(TwContent twContent);

    boolean delete(int id);

    TwContent find(int id);
}
