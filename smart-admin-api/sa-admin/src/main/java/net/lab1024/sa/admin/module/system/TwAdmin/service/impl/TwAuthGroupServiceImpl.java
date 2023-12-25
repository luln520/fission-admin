package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthGroupAccessDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthGroupDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroup;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroupAccess;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthGroupAccessService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthGroupService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwAuthGroup)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:19:04
 */
@Service("twAuthGroupService")
public class TwAuthGroupServiceImpl extends ServiceImpl<TwAuthGroupDao, TwAuthGroup> implements TwAuthGroupService {

}
