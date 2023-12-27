package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMarketDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMenuDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMenu;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMarketService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMenuService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:26:23
 */
@Service("twMenuService")
public class TwMenuServiceImpl extends ServiceImpl<TwMenuDao, TwMenu> implements TwMenuService {

}
