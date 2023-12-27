package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCtmarketDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwDaohangDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwDaohang;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCtmarketService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwDaohangService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwDaohang)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:22:02
 */
@Service("twDaohangService")
public class TwDaohangServiceImpl extends ServiceImpl<TwDaohangDao, TwDaohang> implements TwDaohangService {

}
