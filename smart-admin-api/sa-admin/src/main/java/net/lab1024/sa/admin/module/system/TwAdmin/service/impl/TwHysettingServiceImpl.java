package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHysettingDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHyorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHysettingService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 合约参数(TwHysetting)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:23:33
 */
@Service("twHysettingService")
public class TwHysettingServiceImpl extends ServiceImpl<TwHysettingDao, TwHysetting> implements TwHysettingService {

}
