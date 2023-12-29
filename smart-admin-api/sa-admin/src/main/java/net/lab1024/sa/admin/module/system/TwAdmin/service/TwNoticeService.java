package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 通知表(TwNotice)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
public interface TwNoticeService extends IService<TwNotice> {
    IPage<TwNotice> listpage(PageParam pageParam);
}
