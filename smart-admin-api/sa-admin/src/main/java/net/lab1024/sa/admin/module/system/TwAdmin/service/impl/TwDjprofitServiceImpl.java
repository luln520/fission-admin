package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwDaohangDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwDjprofitDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwDaohang;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwDjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwDaohangService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwDjprofitService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 数字币冻结记录表(TwDjprofit)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:22:15
 */
@Service("twDjprofitService")
public class TwDjprofitServiceImpl extends ServiceImpl<TwDjprofitDao, TwDjprofit> implements TwDjprofitService {

}
