package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMyzc2Dao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMyzcDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc2;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMyzc2Service;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMyzcService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 提币表(TwMyzc)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
@Service("twMyzcService")
public class TwMyzcServiceImpl extends ServiceImpl<TwMyzcDao, TwMyzc> implements TwMyzcService {

}
