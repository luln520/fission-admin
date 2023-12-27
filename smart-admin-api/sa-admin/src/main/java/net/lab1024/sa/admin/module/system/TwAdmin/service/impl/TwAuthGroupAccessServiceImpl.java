package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthExtendDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthGroupAccessDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthExtend;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroupAccess;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthExtendService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthGroupAccessService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwAuthGroupAccess)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:19:21
 */
@Service("twAuthGroupAccessService")
public class TwAuthGroupAccessServiceImpl extends ServiceImpl<TwAuthGroupAccessDao, TwAuthGroupAccess> implements TwAuthGroupAccessService {

}
