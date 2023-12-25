package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTradeLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTradeLog;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeLogService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwTradeLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:13
 */
@Service("twTradeLogService")
public class TwTradeLogServiceImpl extends ServiceImpl<TwTradeLogDao, TwTradeLog> implements TwTradeLogService {

}
