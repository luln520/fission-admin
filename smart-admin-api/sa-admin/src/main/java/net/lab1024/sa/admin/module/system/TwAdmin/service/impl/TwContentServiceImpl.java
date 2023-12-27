package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwConfigDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwContentDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwConfigService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwContentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 公告内容(TwContent)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@Service("twContentService")
public class TwContentServiceImpl extends ServiceImpl<TwContentDao, TwContent> implements TwContentService {

}
