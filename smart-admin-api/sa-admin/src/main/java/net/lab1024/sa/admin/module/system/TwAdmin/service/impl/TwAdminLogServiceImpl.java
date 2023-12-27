package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAdminLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinCommentDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinCommentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 后台管理员操作日志表(TwAdminLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:17:09
 */
@Service("twAdminLogService")
public class TwAdminLogServiceImpl extends ServiceImpl<TwAdminLogDao, TwAdminLog> implements TwAdminLogService {

}
