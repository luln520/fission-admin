package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTradeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTyhyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTrade;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTyhyorderService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 合约订单表(TwTyhyorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:27
 */
@Service("twTyhyorderService")
public class TwTyhyorderServiceImpl extends ServiceImpl<TwTyhyorderDao, TwTyhyorder> implements TwTyhyorderService {

}
