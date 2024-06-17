package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwDjprofitDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwFooterDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwDjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwFooter;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwDjprofitService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwFooterService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwFooter)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:22:59
 */
@Service("twFooterService")
public class TwFooterServiceImpl extends ServiceImpl<TwFooterDao, TwFooter> implements TwFooterService {

}
