package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwConfigDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwConfigService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 网站配置表(TwConfig)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:16
 */
@Service("twConfigService")
public class TwConfigServiceImpl extends ServiceImpl<TwConfigDao, TwConfig> implements TwConfigService {

    @Override
    public TwConfig find(int id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",1);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean addOrUpdate(TwConfig twConfig) {
        return this.saveOrUpdate(twConfig);
    }
}
