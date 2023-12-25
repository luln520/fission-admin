package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserLogService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户记录表(TwUserLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@Service("twUserLogService")
public class TwUserLogServiceImpl extends ServiceImpl<TwUserLogDao, TwUserLog> implements TwUserLogService {

}
