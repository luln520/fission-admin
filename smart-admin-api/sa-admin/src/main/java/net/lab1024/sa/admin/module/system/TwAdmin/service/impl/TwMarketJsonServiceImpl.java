package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKuangjiDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMarketJsonDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarketJson;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKuangjiService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMarketJsonService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwMarketJson)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:25:08
 */
@Service("twMarketJsonService")
public class TwMarketJsonServiceImpl extends ServiceImpl<TwMarketJsonDao, TwMarketJson> implements TwMarketJsonService {

}
