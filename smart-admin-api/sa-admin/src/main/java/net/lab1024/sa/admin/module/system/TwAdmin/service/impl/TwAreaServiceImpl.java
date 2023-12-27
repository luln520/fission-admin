package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAppcDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAreaDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAppc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwArea;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAppcService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAreaService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwArea)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:18:24
 */
@Service("twAreaService")
public class TwAreaServiceImpl extends ServiceImpl<TwAreaDao, TwArea> implements TwAreaService {

}
